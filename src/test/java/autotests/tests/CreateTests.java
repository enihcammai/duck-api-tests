package autotests.tests;

import autotests.client.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateTests extends DuckActionsClient {

    @Test(description = "Проверка создания резиновой уточки")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED");
        validateFullBodyResponse(runner, "material", "rubber", HttpStatus.OK);
    }

    @Test(description = "Проверка создания деревянной уточки")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "red", 0.03, "wood", "quack", "FIXED");
        validateFullBodyResponse(runner, "material", "wood", HttpStatus.OK);
    }
}
