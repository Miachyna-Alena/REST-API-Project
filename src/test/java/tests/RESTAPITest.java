package tests;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Ordering;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.PostModel;
import models.UserModel;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.APIUtils;
import utils.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertTrue;

public class RESTAPITest extends BaseTest {
    private static final Logger LOGGER = AqualityServices.getLogger();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final ISettingsFile TEST_DATA = new JsonSettingsFile("test-data.json");
    private static final String PATH_TO_USER_DATA_FILE = TEST_DATA.getValue("/PATH_TO_USER_DATA_FILE").toString();
    private static final String POSTS_ENDPOINT = TEST_DATA.getValue("/POSTS_ENDPOINT").toString();
    private static final String USERS_ENDPOINT = TEST_DATA.getValue("/USERS_ENDPOINT").toString();
    private static final int VALID_POST_ID = (int) TEST_DATA.getValue("/VALID_POST_ID");
    private static final int INVALID_POST_ID = (int) TEST_DATA.getValue("/INVALID_POST_ID");
    private static final int POST_USERID = (int) TEST_DATA.getValue("/POST_USERID");
    private static final int POST_ID = (int) TEST_DATA.getValue("/POST_ID");
    private static final int NEW_POST_USERID = (int) TEST_DATA.getValue("/NEW_POST_USERID");
    private static final int NEW_POST_TITLE_LENGTH = (int) TEST_DATA.getValue("/NEW_POST_TITLE_LENGTH");
    private static final String NEW_POST_TITLE = RandomUtils.generateRandoString(NEW_POST_TITLE_LENGTH);
    private static final int NEW_POST_BODY_LENGTH = (int) TEST_DATA.getValue("/NEW_POST_BODY_LENGTH");
    private static final String NEW_POST_BODY = RandomUtils.generateRandoString(NEW_POST_BODY_LENGTH);
    private static final int USER_ID = (int) TEST_DATA.getValue("/USER_ID");

    @Test
    public static void testRESTAPI() throws IOException {
        SoftAssert softAssert = new SoftAssert();

        LOGGER.info("Send GET request to get all posts.");
        Response all_posts_response = APIUtils.get(POSTS_ENDPOINT);
        all_posts_response
                .then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON);

        LOGGER.info("Get the posts ID list.");
        List<String> postsID = all_posts_response.jsonPath().getList("id");
        assertTrue(Ordering.natural().isOrdered(postsID), "Posts are sorted NOT by ID ascending.");

        LOGGER.info("Send GET request to get post with given valid ID.");
        Response valid_post_response = APIUtils.get(POSTS_ENDPOINT + VALID_POST_ID);
        valid_post_response
                .then()
                .assertThat().statusCode(200);

        PostModel valid_post = valid_post_response.as(PostModel.class);

        softAssert.assertEquals(valid_post.getUserId(), POST_USERID, "User ID is NOT 10.");
        softAssert.assertEquals(valid_post.getId(), POST_ID, "Post ID is NOT 99.");
        softAssert.assertEquals(valid_post.getTitle(), notNullValue(), "Title is EMPTY.");
        softAssert.assertEquals(valid_post.getBody(), notNullValue(), "Body is EMPTY.");

        LOGGER.info("Send GET request to get post with given invalid ID.");
        APIUtils.get(POSTS_ENDPOINT + INVALID_POST_ID)
                .then()
                .assertThat().statusCode(404)
                .assertThat().body("isEmpty()", is(true));

        LOGGER.info("Create post body with given user ID, random body and random title.");
        PostModel post = new PostModel(NEW_POST_USERID, NEW_POST_TITLE, NEW_POST_BODY);
        String requestBody = objectMapper.writeValueAsString(post);

        LOGGER.info("Send POST request to create new post.");
        Response new_post_response = APIUtils.post(requestBody, POSTS_ENDPOINT);
        new_post_response
                .then()
                .assertThat().statusCode(201)
                .assertThat().body("$", hasKey("id"));

        PostModel new_post = new_post_response.as(PostModel.class);

        softAssert.assertEquals(new_post.getUserId(), NEW_POST_USERID);
        softAssert.assertEquals(new_post.getTitle(), NEW_POST_TITLE);
        softAssert.assertEquals(new_post.getBody(), NEW_POST_BODY);

        LOGGER.info("Send GET request to get all users.");
        Response all_users_response = APIUtils.get(USERS_ENDPOINT);
        all_users_response
                .then()
                .assertThat().statusCode(200)
                .assertThat().contentType(ContentType.JSON);

        LOGGER.info("Get user with given ID.");
        UserModel filtered_user = all_users_response.jsonPath().getList("$", UserModel.class).stream()
                .filter(x -> x.getId() == USER_ID)
                .findFirst()
                .orElse(null);

        File file = new File(PATH_TO_USER_DATA_FILE);
        UserModel user = objectMapper.readValue(file, UserModel.class);

        softAssert.assertEquals(user, filtered_user, "User data from response NOT matches with given user data.");

        LOGGER.info("Send GET request to get user with given ID.");
        Response user_with_given_id_response = APIUtils.get(USERS_ENDPOINT + USER_ID);
        user_with_given_id_response
                .then()
                .assertThat().statusCode(200);

        UserModel user_with_given_id = user_with_given_id_response.as(UserModel.class);

        softAssert.assertEquals(user, user_with_given_id, "User data from response NOT matches with given user data.");
    }
}
