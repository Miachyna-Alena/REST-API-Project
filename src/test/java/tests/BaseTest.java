package tests;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected static final ISettingsFile CONFIG_DATA = new JsonSettingsFile("config-data.json");
    private static final String URL = CONFIG_DATA.getValue("/URL").toString();

    @BeforeMethod
    public static void setup() {
        RestAssured.baseURI = URL;
    }
}
