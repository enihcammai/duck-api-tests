package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


public class SwimTests extends DuckActionsClient {
    @Test(description = "Проверка того, что уточка не поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        String duckId = getDuckId(runner);

        duckSwim(runner, duckId);
        validateSingleMessageResponse(runner, "message", "Paws are not found ((((", HttpStatus.NOT_FOUND);
    }


    @Test(description = "Проверка того, что несуществующая уточка не поплыла")
    @CitrusTest
    public void nonExistingDuckSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        duckSwim(runner, "100");
        validateSingleMessageResponse(runner, "message", "Paws are not found ((((", HttpStatus.NOT_FOUND);
    }
}
