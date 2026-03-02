package createcourier;

import config.TestBase;
import model.CourierCreateRequest;
import model.CourierLoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class CreateCourierTests extends TestBase {

    private Integer courierId;

    @AfterEach
    void tearDown() {
        if (courierId != null) {
            new CourierSteps().deleteCourier(courierId);
        }
    }

    @Test
    void checkingCourierCreation() {
        String login = "login_" + System.currentTimeMillis();
        String password = "pass_" + System.currentTimeMillis();

        CourierSteps courierSteps = new CourierSteps();

        CourierCreateRequest createBody = new CourierCreateRequest(login, password, "name");
        courierSteps.assertCourierCreated(courierSteps.createCourier(createBody));

        CourierLoginRequest loginBody = new CourierLoginRequest(login, password);
        courierId = courierSteps.extractCourierId(
                courierSteps.loginCourier(loginBody)
        );
    }

    @Test
    void shouldNotCreateDuplicateCourier() {
        String login = "login_" + System.currentTimeMillis();
        String password = "pass_" + System.currentTimeMillis();

        CourierSteps courierSteps = new CourierSteps();

        CourierCreateRequest createBody = new CourierCreateRequest(login, password, "name");
        courierSteps.assertCourierCreated(courierSteps.createCourier(createBody));

        courierSteps.createCourier(createBody)
                .statusCode(409);

        CourierLoginRequest loginBody = new CourierLoginRequest(login, password);
        courierId = courierSteps.extractCourierId(
                courierSteps.loginCourier(loginBody)
        );
    }

    @Test
    void shouldReturnErrorIfPasswordMissing() {
        String login = "login_" + System.currentTimeMillis();

        CourierSteps courierSteps = new CourierSteps();

        CourierCreateRequest createBody = new CourierCreateRequest(login, null, "name");

        courierSteps.createCourier(createBody)
                .statusCode(400);
    }
}
