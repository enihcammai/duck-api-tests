package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;

@Epic("Тесты на duck-controller")
@Feature("Удаление уточки")
@Story("Эндпоинт /api/duck/delete")
public class DeleteTests extends DuckActionsClient {

    @Test(description = "Удалить утку")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        getDuckId1(runner);

        databaseUpdate(runner, """
                delete from DUCK
                where id = ${duckId};
                """);

        runner.$(query(testDb)
                .statement("SELECT COUNT(*) as count FROM DUCK WHERE id = ${duckId}")
                .validate("count", "0")
        );
    }
}
