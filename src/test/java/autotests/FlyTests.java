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

public class FlyTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(description = "Проверка того, что уточка со связанными крыльями не полетела")
    @CitrusTest
    public void flyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "yellow", 0.1, "rubber", "quack", "FIXED", URL);
        String duckId = DuckIdService.getDuckId(runner, URL);

        GetCalls.duckFly(runner, duckId, URL);
        Validator.validateSingleMessageResponse(runner, "message", "I can not fly :C", HttpStatus.OK, URL);
    }

    @Test(description = "Проверка того, что уточка с активными крыльями полетела")
    @CitrusTest
    public void flyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "yellow", 0.05, "rubber", "quack", "ACTIVE", URL);
        String duckId = DuckIdService.getDuckId(runner, URL);

        GetCalls.duckFly(runner, duckId, URL);
        Validator.validateSingleMessageResponse(runner, "message", "I am flying :)", HttpStatus.OK, URL);
    }

    @Test(description = "Проверка того, что у уточки с неописанными крыльями не найдены крылья")
    @CitrusTest
    public void flyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "yellow", 0.08, "rubber", "quack", "UNDEFINED", URL);
        String duckId = DuckIdService.getDuckId(runner, URL);

        GetCalls.duckFly(runner, duckId, URL);
        Validator.validateSingleMessageResponse(runner, "message", "Wings are not detected :(", HttpStatus.OK, URL);
    }


}
