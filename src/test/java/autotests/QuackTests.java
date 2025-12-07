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
import services.Validator;

public class QuackTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(priority = 1)
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        GetCalls.duckQuack(runner, "1", "1", "1", URL);
        Validator.validateSingleMessageResponse(runner, "sound", "quack", HttpStatus.OK, URL);
    }

    @Test(priority = 2)
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        GetCalls.duckQuack(runner, "2", "1", "1", URL);
        Validator.validateSingleMessageResponse(runner, "sound", "moo", HttpStatus.OK, URL);
    }
}
