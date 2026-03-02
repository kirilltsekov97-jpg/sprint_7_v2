package createcourier;

import config.TestBase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderListTests extends TestBase {

    @Test
    void shouldReturnOrdersList() {
        new OrderSteps()
                .getOrdersList()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
