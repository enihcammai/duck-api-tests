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
        validateResources(runner, "getDuckPropertiesTest/checkDuckID", HttpStatus.OK);
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
        validateFullBodyResponse(runner, "red", 0.03, "wood", "quack", "FIXED", HttpStatus.OK);
    }
}
