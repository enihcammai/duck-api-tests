package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckSingleResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


@Epic("Тесты на duck-controller")
@Feature("Изменение уточки")
@Story("Эндпоинт /api/duck/update")
public class UpdateTests extends DuckActionsClient {

    @Test(description = "Изменить цвет и высоту уточки")
    @CitrusTest
    public void successfulChangeOfColorAndHeight(@Optional @CitrusResource TestCaseRunner runner) {
        createDuckInDB(runner, "red", "0.03", "rubber", "quack", "ACTIVE");
        updateDuck(runner, "${duckId}", "yellow", "0.06", "rubber", "quack", "FIXED");

        DuckSingleResponse response = new DuckSingleResponse().message("Duck with id = ${duckId} is updated");
        validatePayload(runner, response, HttpStatus.OK);

        validateDuckInDatabase(runner, "${duckId}", "yellow", "0.06", "rubber", "quack", "FIXED");

    }

    @Test(description = "Изменить цвет и звук уточки")
    @CitrusTest
    public void successfulChangeOfColorAndSound(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "FIXED");
        updateDuck(runner, "${duckId}", "yellow", "0.03", "rubber", "woof", "FIXED");

        DuckSingleResponse response = new DuckSingleResponse().message("Duck with id = ${duckId} is updated");
        validatePayload(runner, response, HttpStatus.OK);

        getDuckProperties(runner, "${duckId}");
        validateResources(runner, "updateDuckPropertiesTest/editedWoofDuck.json", HttpStatus.OK);
    }
}
