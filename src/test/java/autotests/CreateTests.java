package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import post.PostCalls;
import services.Validator;

public class CreateTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(description = "Проверка создания резиновой уточки")
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        Validator.validateFullBodyResponse(runner, "material", "rubber", HttpStatus.OK, URL);
    }

    @Test(description = "Проверка создания деревянной уточки")
    @CitrusTest
    public void createWoodDuck(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "wood", "quack", "FIXED", URL);
        Validator.validateFullBodyResponse(runner, "material", "wood", HttpStatus.OK, URL);
    }
}
