package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import get.GetCalls;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import post.PostCalls;
import services.DuckIdService;
import services.Validator;


public class SwimTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(description = "Проверка того, что уточка не поплыла")
    @CitrusTest
    public void successfulSwim(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        String duckId = DuckIdService.getDuckId(runner, URL);

        GetCalls.duckSwim(runner, duckId, URL);
        Validator.validateSingleMessageResponse(runner, "message", "Paws are not found ((((", HttpStatus.NOT_FOUND, URL);
    }


    @Test(description = "Проверка того, что несуществующая уточка не поплыла")
    @CitrusTest
    public void nonExistingDuckSwim(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        GetCalls.duckSwim(runner, "100", URL);
        Validator.validateSingleMessageResponse(runner, "message", "Paws are not found ((((", HttpStatus.NOT_FOUND, URL);
    }
}
