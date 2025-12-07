package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyTests extends DuckActionsClient {

    @Test(description = "Проверка того, что уточка со связанными крыльями не полетела")
    @CitrusTest
    public void flyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.1, "rubber", "quack", "FIXED");
        String duckId = getDuckId(runner);

        duckFly(runner, duckId);
        validateSingleMessageResponse(runner, "message", "I can not fly :C", HttpStatus.OK);
    }

    @Test(description = "Проверка того, что уточка с активными крыльями полетела")
    @CitrusTest
    public void flyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.05, "rubber", "quack", "ACTIVE");
        String duckId = getDuckId(runner);

        duckFly(runner, duckId);
        validateSingleMessageResponse(runner, "message", "I am flying :)", HttpStatus.OK);
    }

    @Test(description = "Проверка того, что у уточки с неописанными крыльями не найдены крылья")
    @CitrusTest
    public void flyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.08, "rubber", "quack", "UNDEFINED");
        String duckId = getDuckId(runner);

        duckFly(runner, duckId);
        validateSingleMessageResponse(runner, "message", "Wings are not detected :(", HttpStatus.OK);
    }
}
