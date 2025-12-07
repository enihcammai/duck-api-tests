package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class PropertiesTests extends DuckActionsClient {

    @Test(priority = 2, description = "Проверка того, что у четной уточки вернулись правильные свойства")
    @CitrusTest
    public void successfulGetEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.03, "wood", "quack", "FIXED");
        duckProperties(runner, "2");
        validateEmptyBodyResponse(runner, HttpStatus.OK);
    }

    @Test(priority = 1, description = "Проверка того, что у нечетной уточки вернулись правильные свойства")
    @CitrusTest
    public void successfulGetOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        duckProperties(runner, "1");
        validateFullBodyResponse(runner, "material", "rubber", HttpStatus.OK);
    }

}
