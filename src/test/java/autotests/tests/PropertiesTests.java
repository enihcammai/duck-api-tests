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


public class PropertiesTests extends DuckActionsClient {

    // TODO: SHIFT-AQA-2
    @Test(priority = 2, description = " ID - целое четное число. Есть в БД (утка с material = wood)")
    @CitrusTest
    public void successfulGetEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context){
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("wood")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        String duckId = getDuckId(runner, context);

        while(Integer.parseInt(duckId) % 2 != 0){
            createDuck(runner, duckProperties);
            duckId = getDuckId(runner, context);
        }
        getDuckProperties(runner, duckId);
        validateEmptyBodyResponseWithString(runner, HttpStatus.OK);
    }

    @Test(priority = 1, description = "ID - целое нечетное число. Есть в БД (утка с material = rubber)")
    @CitrusTest
    public void successfulGetOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context){
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        String duckId = getDuckId(runner, context);

        while(Integer.parseInt(duckId) % 2 == 0){
            createDuck(runner, duckProperties);
            duckId = getDuckId(runner, context);
        }
        getDuckProperties(runner, duckId);
        validateResources(runner, "getDuckPropertiesTest/checkCreatedDuck.json", HttpStatus.OK);
    }

}
