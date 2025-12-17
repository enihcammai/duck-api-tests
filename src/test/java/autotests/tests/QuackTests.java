package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckSoundSingleResponse;
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


@Epic("Тесты на duck-action-controller")
@Feature("Кряканье уточки")
@Story("Эндпоинт /api/duck/action/quack")
public class QuackTests extends DuckActionsClient {

    @Test(priority = 1, description = "Корректный нечётный id, корректный звук")
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        int duckId = createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "ACTIVE");

        while(duckId % 2 == 0){
            duckId = createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "ACTIVE");
        }
        duckQuack(runner, String.valueOf(duckId), "1", "1");
        DuckSoundSingleResponse response = new DuckSoundSingleResponse().sound("quack");
        validatePayload(runner, response, HttpStatus.OK);
    }

    // TODO: SHIFT-AQA-3
    @Test(priority = 2, description = "Корректный чётный id, корректный звук")
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        int duckId = createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "ACTIVE");

        while(duckId % 2 != 0){
            duckId = createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "ACTIVE");
        }
        duckQuack(runner, String.valueOf(duckId), "1", "1");
        DuckSoundSingleResponse response = new DuckSoundSingleResponse().sound("moo");
        validatePayload(runner, response, HttpStatus.OK);
    }
}
