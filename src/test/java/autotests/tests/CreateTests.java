package autotests.tests;

import autotests.clients.DuckActionsClient;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

@Epic("Тесты на duck-controller")
@Feature("Создание уточки")
@Story("Эндпоинт /api/duck/create")
public class CreateTests extends DuckActionsClient {
    DuckProperties yellowDuckProperties = new DuckProperties()
            .color("yellow")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");
    DuckProperties greenDuckProperties = new DuckProperties()
            .color("green")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");
    DuckProperties redDuckProperties = new DuckProperties()
            .color("red")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");
    DuckProperties purpleDuckProperties = new DuckProperties()
            .color("purple")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");
    DuckProperties orangeDuckProperties = new DuckProperties()
            .color("orange")
            .height(0.03)
            .material("rubber")
            .sound("quack")
            .wingsState("FIXED");

    @Test(description = "Создать резиновую утку", dataProvider = "colorDuckList")
    @CitrusTest
    @CitrusParameters({"payload", "response", "runner"})
    public void createRubberDuck(Object payload, String response, @Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, payload);
        validateResources(runner, response, HttpStatus.OK);
    }

    @Test(description = "Создать деревянную утку")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        DuckProperties woodDuck = new DuckProperties()
                .color("red")
                .height(0.03)
                .material("rubber")
                .sound("quack")
                .wingsState("FIXED");
        createDuck(runner, woodDuck);
        extractId(runner);
        validateDuckInDatabase(runner, "${duckId}", "red", "0.03", "rubber", "quack", "FIXED");
    }

    @DataProvider(name = "colorDuckList")
    public Object[][] duckProvider() {
        return new Object[][]{
                {yellowDuckProperties, "getDuckPropertiesTest/yellowDuck.json", null},
                {greenDuckProperties, "getDuckPropertiesTest/greenDuck.json", null},
                {redDuckProperties, "getDuckPropertiesTest/redDuck.json", null},
                {purpleDuckProperties, "getDuckPropertiesTest/purpleDuck.json", null},
                {orangeDuckProperties, "getDuckPropertiesTest/orangeDuck.json", null}
        };
    }
}
