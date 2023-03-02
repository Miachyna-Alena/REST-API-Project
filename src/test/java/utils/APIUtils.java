package utils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class APIUtils {
    public static Response get(String endpoint) {
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post(String requestBody, String endpoint) {
        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }
}
