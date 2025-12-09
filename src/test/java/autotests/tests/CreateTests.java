package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class CreateTests extends DuckActionsClient {

    @Test(description = "Создать резиновую утку")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context) {
        DuckProperties duckPropertiesForCreate = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckPropertiesForCreate);

        String duckId = getDuckId(runner, context);

        DuckProperties duckPropertiesForValidation = new DuckProperties()
                .color("red")
                .height(3.0)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        getDuckProperties(runner, duckId);
        validatePayload(runner, duckPropertiesForValidation, HttpStatus.OK);
    }


    @Test(description = "Создать деревянную утку")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        // В общем id он не хочет воспринимать, поэтому проверяю все остальные поля
        validateFullBodyResponse(runner, "red", 0.03, "wood", "quack", "FIXED", HttpStatus.OK);
    }
}
