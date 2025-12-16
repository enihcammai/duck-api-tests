package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.DuckSingleResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class SwimTests extends DuckActionsClient {

    // TODO: SHIFT-AQA-1
    @Test(description = "Существующий id ")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        String duckId = getDuckId(runner, context);

        duckSwim(runner, duckId);
        DuckSingleResponse response = new DuckSingleResponse().message("Paws are not found ((((");
        validatePayload(runner, response, HttpStatus.NOT_FOUND);
    }


    @Test(description = "Несуществующий id")
    @CitrusTest
    public void nonExistingDuckSwim(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        String duckId = getDuckId(runner, context);

        deleteDuck(runner, duckId);
        duckSwim(runner, duckId);
        DuckSingleResponse response = new DuckSingleResponse().message("Paws are not found ((((");
        validatePayload(runner, response, HttpStatus.NOT_FOUND);
    }
}
