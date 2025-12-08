package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.DuckSingleResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateTests extends DuckActionsClient {

    @Test(description = "Изменить цвет и высоту уточки")
    @CitrusTest
    public void successfulChangeOfColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("color", "yellow");
        runner.variable("height", "0.06");

        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);

        String duckId = getDuckId(runner);
        updateDuck(runner, duckId, "${color}", "${height}", "rubber", "quack", "FIXED");

        DuckSingleResponse response = new DuckSingleResponse().message("Duck with id = " + duckId + " is updated");
        validateSingleMessageResponseWithPayload(runner, response, HttpStatus.OK);

        getDuckProperties(runner, duckId);
        validateResponseWithResources(runner, "updateDuckPropertiesTest/editedYellowDuck.json", HttpStatus.OK);
    }

    @Test(description = "Изменить цвет и звук уточки")
    @CitrusTest
    public void successfulChangeOfColorAndSound(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("color", "yellow");
        runner.variable("sound", "woof");

        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);

        String duckId = getDuckId(runner);
        updateDuck(runner, duckId, "${color}", "0.03", "rubber", "${sound}", "FIXED");

        DuckSingleResponse response = new DuckSingleResponse().message("Duck with id = " + duckId + " is updated");
        validateSingleMessageResponseWithPayload(runner, response, HttpStatus.OK);

        getDuckProperties(runner, duckId);
        validateResponseWithResources(runner, "updateDuckPropertiesTest/editedWoofDuck.json", HttpStatus.OK);
    }
}
