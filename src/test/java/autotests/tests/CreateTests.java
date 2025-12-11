package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
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
@Feature("Создание уточки")
@Story("Эндпоинт /api/duck/create")
public class CreateTests extends DuckActionsClient {

    @Test(description = "Создать резиновую утку")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckPropertiesForCreate = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckPropertiesForCreate);


        getDuckId1(runner);
        DuckProperties duckPropertiesForValidation = new DuckProperties()
                .color("red")
                .height(3.0)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        getDuckProperties(runner, "${duckId}");
        validatePayload(runner, duckPropertiesForValidation, HttpStatus.OK);
    }

    @Test(description = "Создать деревянную утку")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (1338, 'orange', 3.0, 'cheese', 'hrum','ACTIVE');");

        validateDuckInDatabase(runner, "1234", "orange", "3.0", "cheese", "hrum", "ACTIVE");
    }
}
