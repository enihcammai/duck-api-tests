package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateTests extends DuckActionsClient {

    @Test(description = "Проверка обновления у уточки цвета и высоты")
    @CitrusTest
    public void successfulChangeOfColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("color", "yellow");
        runner.variable("height", "0.06");

        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");

        String duckId = getDuckId(runner);
        updateDuck(runner, duckId, "${color}", "${height}", "rubber", "quack", "FIXED");

        validateSingleMessageResponse(runner, "message", "Duck with id = " + duckId + " is updated", HttpStatus.OK);
        duckProperties(runner, duckId);

        multipleValidate(runner, "color", "height", "${color}", "6.0", HttpStatus.OK);
    }

    @Test(description = "Проверка обновления у уточки цвета и звука")
    @CitrusTest
    public void successfulChangeOfColorAndSound(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("color", "yellow");
        runner.variable("sound", "woof");

        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");

        String duckId = getDuckId(runner);
        updateDuck(runner, duckId, "${color}", "0.03", "rubber", "${sound}", "FIXED");

        validateSingleMessageResponse(runner, "message", "Duck with id = " + duckId + " is updated", HttpStatus.OK);
        duckProperties(runner, duckId);
        multipleValidate(runner, "color", "sound", "${color}", "${sound}", HttpStatus.OK);
    }
}
