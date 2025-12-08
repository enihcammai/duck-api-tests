package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesTests extends DuckActionsClient {

    // TODO: SHIFT-AQA-2
    @Test(priority = 2, description = " ID - целое четное число. Есть в БД (утка с material = wood)")
    @CitrusTest
    public void successfulGetEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner){
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        getDuckProperties(runner, "2");
        validateEmptyBodyResponseWithString(runner, HttpStatus.OK);
    }

    @Test(priority = 1, description = "ID - целое нечетное число. Есть в БД (утка с material = rubber)")
    @CitrusTest
    public void successfulGetOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner){
        DuckProperties duckProperties = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        getDuckProperties(runner, "3");
        validateFullBodyResponseWithResources(runner, "getDuckPropertiesTest/checkCreatedDuck.json", HttpStatus.OK);
    }

}
