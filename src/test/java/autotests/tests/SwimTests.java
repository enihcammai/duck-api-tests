package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
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


@Epic("Тесты на duck-action-controller")
@Feature("Плавание уточки")
@Story("Эндпоинт /api/duck/action/swim")
public class SwimTests extends DuckActionsClient {

    // TODO: SHIFT-AQA-1
    @Test(description = "Существующий id ")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "ACTIVE");
        duckSwim(runner, "${duckId}");
        DuckSingleResponse response = new DuckSingleResponse().message("Paws are not found ((((");
        validatePayload(runner, response, HttpStatus.NOT_FOUND);
    }


    @Test(description = "Несуществующий id")
    @CitrusTest
    public void nonExistingDuckSwim(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "ACTIVE");
        databaseUpdate(runner, """
                delete from DUCK
                where id = ${duckId};
                """);
        duckSwim(runner, "${duckId}");
        DuckSingleResponse response = new DuckSingleResponse().message("Paws are not found ((((");
        validatePayload(runner, response, HttpStatus.NOT_FOUND);
    }
}
