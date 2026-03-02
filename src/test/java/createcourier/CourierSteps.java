package createcourier;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import config.TestBase;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierSteps extends TestBase{

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierCreateRequest body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step("Проверить, что курьер создан: status code 201 и ok=true")
    public void assertCourierCreated(ValidatableResponse response) {
        response.statusCode(201).body("ok", is(true));
    }

    @Step("Логин курьера")
    public ValidatableResponse loginCourier(CourierLoginRequest body) {
        return given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().ifValidationFails();
    }

    @Step("Проверить, что логин успешный (200) и вернуть courierId")
    public int extractCourierId(ValidatableResponse response) {
        return response
                .statusCode(200)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    @Step("Удалить ранее созданного курьера по id")
    public void deleteCourier(int courierId) {
        given()
                .pathParam("id", courierId)
                .when()
                .delete("/api/v1/courier/{id}")
                .then()
                .statusCode(200);
    }

}
