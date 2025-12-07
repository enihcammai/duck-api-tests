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
import services.Validator;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PropertiesTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(priority = 2, description = "1")
    @CitrusTest
    public void successfulGetEvenDuckProperties(@Optional @CitrusResource TestCaseRunner runner){
        PostCalls.createDuck(runner, "red", 0.03, "wood", "quack", "FIXED", URL);

        GetCalls.duckProperties(runner, "2", URL);
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

    @Test(priority = 1, description = "2")
    @CitrusTest
    public void successfulGetOddDuckProperties(@Optional @CitrusResource TestCaseRunner runner){
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        GetCalls.duckProperties(runner, "1", URL);
        Validator.validateFullBodyResponse(runner, "material", "rubber", HttpStatus.OK, URL);
    }

}
