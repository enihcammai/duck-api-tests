package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckSingleResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Удаление уточки")
@Story("Эндпоинт /api/duck/delete")
public class DeleteTests extends DuckActionsClient {

    @Test(description = "Удалить утку")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        createDuckInDB(runner, "yellow", "0.03", "rubber", "quack", "FIXED");
        deleteDuck(runner, "${duckId}");

        DuckSingleResponse response = new DuckSingleResponse().message("Duck is deleted");
        validatePayload(runner, response, HttpStatus.OK);
    }
}