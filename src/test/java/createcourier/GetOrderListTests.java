package createcourier;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

import static io.restassured.RestAssured.given;

public class GetOrderListTests {

    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }
    //возвращаем заказы
    @Test
    void shouldReturnOrdersList() {
        given()
                .when()
                .get("/api/v1/orders")
                .then()
                .statusCode(200)
                .body("orders", notNullValue())  // массив заказов не null
                .body("orders.size()", greaterThan(0));

    }
}
