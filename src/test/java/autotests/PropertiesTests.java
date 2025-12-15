package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import get.GetCalls;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import post.PostCalls;
import services.DuckIdService;
import services.Validator;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PropertiesTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(priority = 2, description = "Проверка того, что у четной уточки вернулись правильные свойства")
    @CitrusTest
    public void successfulGetEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "wood", "quack", "FIXED", URL);

        DuckIdService.extractId(runner, URL);
        while(Integer.parseInt("${duckId}") % 2 != 0){
            PostCalls.createDuck(runner, "red", 0.03, "wood", "quack", "FIXED", URL);
            DuckIdService.extractId(runner, URL);
        }

        GetCalls.duckProperties(runner, "${duckId}", URL);
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{}")
        );
    }

    @Test(priority = 1, description = "Проверка того, что у нечетной уточки вернулись правильные свойства")
    @CitrusTest
    public void successfulGetOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner) {
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);

        DuckIdService.extractId(runner, URL);
        while(Integer.parseInt("${duckId}") % 2 == 0){
            PostCalls.createDuck(runner, "red", 0.03, "wood", "quack", "FIXED", URL);
            DuckIdService.extractId(runner, URL);
        }

        GetCalls.duckProperties(runner, "${duckId}", URL);
        Validator.validateFullBodyResponse(runner, "red", 3.0, "rubber", "quack", "FIXED", HttpStatus.OK, URL);
    }

}
