package createcourier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.OrderCreateRequest;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создать заказ")
    public ValidatableResponse createOrder(OrderCreateRequest body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/orders")
                .then();
    }

    @Step("Получить список заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
