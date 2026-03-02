package config;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestBase {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = System.getProperty(
                "baseUrl",
                "https://qa-scooter.praktikum-services.ru"
        );
        RestAssured.filters(new AllureRestAssured());
    }
}
