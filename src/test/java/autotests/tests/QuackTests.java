package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.DuckSingleResponse;
import autotests.payloads.DuckSoundSingleResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackTests extends DuckActionsClient {

    @Test(priority = 1, description = "Корректный нечётный id, корректный звук")
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        duckQuack(runner, "7", "1", "1");
        DuckSoundSingleResponse response = new DuckSoundSingleResponse().sound("quack");
        validateSoundSingleMessageResponseWithPayload(runner, response, HttpStatus.OK);
    }

    // TODO: SHIFT-AQA-3
    @Test(priority = 2, description = "Корректный чётный id, корректный звук")
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        duckQuack(runner, "2", "1", "1");
        DuckSoundSingleResponse response = new DuckSoundSingleResponse().sound("moo");
        validateSoundSingleMessageResponseWithPayload(runner, response, HttpStatus.OK);
    }
}
