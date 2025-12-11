package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import autotests.payloads.DuckSingleResponse;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;


@Epic("Тесты на duck-action-controller")
@Feature("Полёт уточки")
@Story("Эндпоинт /api/duck/action/fly")
public class FlyTests extends DuckActionsClient {

    @Test(description = "Существующий id со связанными крыльями")
    @CitrusTest
    public void flyWithFixedWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("yellow")
                .height(0.1)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");

        createDuck(runner, duckProperties);
        extractId(runner);

        duckFly(runner, "${duckId}");
        DuckSingleResponse validator = new DuckSingleResponse().message("I can not fly :C");
        validatePayload(runner, validator, HttpStatus.OK);
    }

    @Test(description = "Существующий id с активными крыльями")
    @CitrusTest
    public void flyWithActiveWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("yellow")
                .height(0.1)
                .material("rubber")
                .sound("quack")
                .wingsState("ACTIVE");

        createDuck(runner, duckProperties);
        extractId(runner);

        duckFly(runner, "${duckId}");
        DuckSingleResponse validator = new DuckSingleResponse().message("I am flying :)");
        validatePayload(runner, validator, HttpStatus.OK);
    }

    @Test(description = "Существующий id с крыльями в неопределенном состоянии")
    @CitrusTest
    public void flyWithUndefinedWings(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties duckProperties = new DuckProperties()
                .color("yellow")
                .height(0.1)
                .material("rubber")
                .sound("quack")
                .wingsState("UNDEFINED");

        createDuck(runner, duckProperties);
        extractId(runner);

        duckFly(runner, "${duckId}");
        DuckSingleResponse validator = new DuckSingleResponse().message("Wings are not detected :(");
        validatePayload(runner, validator, HttpStatus.OK);
    }
}
