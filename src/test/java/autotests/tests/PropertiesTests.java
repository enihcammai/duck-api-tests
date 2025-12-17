package autotests.tests;

import autotests.clients.DuckActionsClient;
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
@Feature("Свойства уточки")
@Story("Эндпоинт /api/duck/action/properties")
public class PropertiesTests extends DuckActionsClient {

    // TODO: SHIFT-AQA-2
    @Test(priority = 2, description = " ID - целое четное число. Есть в БД (утка с material = wood)")
    @CitrusTest
    public void successfulGetEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context){
        int duckId = createDuckInDB(runner, "red", "1.5", "wood", "scrl-scrl", "ACTIVE");

        while(duckId % 2 != 0){
            duckId = createDuckInDB(runner, "red", "1.5", "wood", "scrl-scrl", "ACTIVE");
        }
        getDuckProperties(runner, String.valueOf(duckId));
        validateEmptyBodyResponseWithString(runner, HttpStatus.OK);
    }

    @Test(priority = 1, description = "ID - целое нечетное число. Есть в БД (утка с material = rubber)")
    @CitrusTest
    public void successfulGetOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner, @Optional @CitrusResource TestContext context){
        int duckId = createDuckInDB(runner, "red", "0.03", "rubber", "quack", "FIXED");

        while(duckId % 2 == 0){
            duckId = createDuckInDB(runner, "red", "0.03", "rubber", "quack", "FIXED");
        }
        getDuckProperties(runner, String.valueOf(duckId));
        validateResources(runner, "getDuckPropertiesTest/checkCreatedDuck.json", HttpStatus.OK);
    }

}
