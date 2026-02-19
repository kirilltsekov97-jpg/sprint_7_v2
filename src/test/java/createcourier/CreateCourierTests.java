package createcourier;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CreateCourierTests {

    private Integer courierId;

    //задали url и подключаем Allure фильтр
    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        RestAssured.filters(new AllureRestAssured());
    }
    // если получили id курьера, то его можно удалить
    @AfterEach
    void tearDown() {
        if (courierId != null) {
            new CourierSteps().deleteCourier(courierId);
        }
    }


    // проверяем создание курьера
    @Test
    void checkingCourierCreation() {
        String login = "login_" + System.currentTimeMillis(); //уникальный логи
        String password = "pass_" + System.currentTimeMillis(); //уникальный пароль

        //JSON для создания курьера
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

        courierId = courierSteps.extractCourierId(
                courierSteps.loginCourier(loginJson)
        );
    }
    // нельзя создавать дубликат курьера
    @Test
    void shouldNotCreateDuplicateCourier() {
        //
        String login = "login_" + System.currentTimeMillis();
        String password = "pass_" + System.currentTimeMillis();

        String createJson = "{"
                + "\"login\":\"" + login + "\","
                + "\"password\":\"" + password + "\","
                + "\"firstName\":\"name\""
                + "}";

        CourierSteps courierSteps = new CourierSteps();


        courierSteps.assertCourierCreated(courierSteps.createCourier(createJson));


        courierSteps.createCourier(createJson)
                .statusCode(409);


        String loginJson = "{"
                + "\"login\":\"" + login + "\","
                + "\"password\":\"" + password + "\""
                + "}";
        courierId = courierSteps.extractCourierId(courierSteps.loginCourier(loginJson));
    }
    // если нет пароля, то будет ошибка
    @Test
    void shouldReturnErrorIfPasswordMissing() {
        String login = "login_" + System.currentTimeMillis();
        // готовим json без пароля
        String createJsonWithoutPassword = "{"
                + "\"login\":\"" + login + "\","
                + "\"firstName\":\"name\""
                + "}";

        CourierSteps courierSteps = new CourierSteps();

        courierSteps.createCourier(createJsonWithoutPassword)
                .statusCode(400);
    }


}
