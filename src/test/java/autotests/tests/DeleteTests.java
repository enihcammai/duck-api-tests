package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteTests extends DuckActionsClient {

    @Test(description = "Проверка удаления уточки")
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        String duckId = getDuckId(runner);

        deleteDuck(runner, duckId);
        validateSingleMessageResponse(runner, "message", "Duck is deleted", HttpStatus.OK);
    }
}
