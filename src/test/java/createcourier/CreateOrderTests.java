package createcourier;

import config.TestBase;
import model.OrderCreateRequest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateOrderTests extends TestBase {

    static Stream<Arguments> colorsProvider() {
        return Stream.of(
                Arguments.of(List.of("BLACK")),
                Arguments.of(List.of("GREY")),
                Arguments.of(List.of("BLACK", "GREY")),
                Arguments.of((List<String>) null)
        );
    }

    @ParameterizedTest
    @MethodSource("colorsProvider")
    void shouldCreateOrderWithDifferentColors(List<String> colors) {
        OrderCreateRequest orderBody = buildOrderBody(colors);

        given()
                .header("Content-Type", "application/json")
                .body(orderBody)
                .when()
                .post("/api/v1/orders")
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }

    private static OrderCreateRequest buildOrderBody(List<String> colors) {
        OrderCreateRequest r = new OrderCreateRequest();
        r.setFirstName("Лирик");
        r.setLastName("Uchiha");
        r.setAddress("Konoha, 140 apt.");
        r.setMetroStation(5);
        r.setPhone("+7 999 366 31 31");
        r.setRentTime(3);
        r.setDeliveryDate("2026-06-06");
        r.setComment("Saske, come back to Konoha");
        r.setColor(colors); // null / 1 / 2
        return r;
    }
}