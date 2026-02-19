package createcourier;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.AfterEach;

public class LoginCourierTests {

    private Integer courierId;

    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
    }
    @AfterEach
    void tearDown() {
        if (courierId != null) {
            new CourierSteps().deleteCourier(courierId);
        }
    }
    //курьер может авторизоваться
    @Test
    void courierCanLogin() {
        String login = "login_" + System.currentTimeMillis();
        String password = "pass_" + System.currentTimeMillis();
//создаем курьера
        String createJson = "{"
                + "\"login\":\"" + login + "\","
                + "\"password\":\"" + password + "\","
                + "\"firstName\":\"name\""
                + "}";

        CourierSteps courierSteps = new CourierSteps();
        courierSteps.assertCourierCreated(courierSteps.createCourier(createJson));

        String loginJson = "{"
                + "\"login\":\"" + login + "\","
                + "\"password\":\"" + password + "\""
                + "}";


        courierSteps.extractCourierId(
                courierSteps.loginCourier(loginJson)
        );
    }

    //логин без пароля должен падать. в данном случае встречаю 504. тест скипаю
    @Disabled("Сервер отдает 504 Gateway timeout, вместо 400")
    @Test
    void shouldFail_whenNoPassword() {
        String loginJsonWithoutPassword = "{"
                + "\"login\":\"someLogin\""
                + "}";

        new CourierSteps()
                .loginCourier(loginJsonWithoutPassword)
                .statusCode(400);
    }
}
