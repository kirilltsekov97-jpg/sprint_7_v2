package createcourier;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


// проверяем создание заказа
public class CreateOrderTests {

    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }

    static Stream<Arguments> colorsProvider() {
        return Stream.of(
                Arguments.of((Object) new String[]{"BLACK"}),
                Arguments.of((Object) new String[]{"GREY"}),
                Arguments.of((Object) new String[]{"BLACK", "GREY"}),
                Arguments.of((Object) null)
        );
    }

    @ParameterizedTest //делаем разные параменты по цвету
    @MethodSource("colorsProvider")
    void shouldCreateOrderWithDifferentColors(String[] colors) {
        String orderJson = buildOrderJson(colors);

        given()
                .header("Content-Type", "application/json")
                .body(orderJson)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    @Step("Собрать JSON заказа (цвета: {colors})")
    private static String buildOrderJson(String[] colors) {
        String base = "{"
                + "\"firstName\":\"Лирик\","
                + "\"lastName\":\"Uchiha\","
                + "\"address\":\"Konoha, 140 apt.\","
                + "\"metroStation\":5,"
                + "\"phone\":\"+7 999 366 31 31\","
                + "\"rentTime\":3,"
                + "\"deliveryDate\":\"2026-06-06\","
                + "\"comment\":\"Saske, come back to Konoha\"";

        if (colors == null) {
            return base + "}";
        }

        StringBuilder colorArray = new StringBuilder();
        for (int i = 0; i < colors.length; i++) {
            if (i > 0) colorArray.append(",");
            colorArray.append("\"").append(colors[i]).append("\"");
        }

        return base + ",\"color\":[" + colorArray + "]}";
    }
}
