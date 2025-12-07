package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackTests extends DuckActionsClient {

    @Test(priority = 1, description = "Проверка звука у нечетной уточки")
    @CitrusTest
    public void oddQuack(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        duckQuack(runner, "1", "1", "1");
        validateSingleMessageResponse(runner, "sound", "quack", HttpStatus.OK);
    }

    @Test(priority = 2, description = "Проверка звука у четной уточки")
    @CitrusTest
    public void evenQuack(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        duckQuack(runner, "2", "1", "1");
        validateSingleMessageResponse(runner, "sound", "moo", HttpStatus.OK);
    }
}
