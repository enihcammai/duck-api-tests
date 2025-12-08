package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckFullBodyResponse;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.context.TestContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestContextManager;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateTests extends DuckActionsClient {

    @Test(description = "Создать резиновую утку")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner, @CitrusResource TestContext context) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        validateFullBodyResponse(runner, "material", "rubber", HttpStatus.OK);

        //Функционал с потенциалом
//        String duckId = getDuckId(runner);
//        String unchainedDuckId = duckId.replace("${", "").replace("}", "");
//        String duckIdValue = context.getVariable(unchainedDuckId);
//        int duckIdInt = Integer.parseInt(duckIdValue);
//
//        DuckFullBodyResponse response = new DuckFullBodyResponse()
//                .id(duckIdInt)
//                .color("red")
//                .height(3.0)
//                .material("rubber")
//                .sound("quack")
//                .wingsState("FIXED");
//
//        validateFullBodyResponseWithPayload(runner, response, HttpStatus.OK);
    }

    @Test(description = "Создать деревянную утку")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        validateFullBodyResponse(runner, "material", "wood", HttpStatus.OK);
    }
}
