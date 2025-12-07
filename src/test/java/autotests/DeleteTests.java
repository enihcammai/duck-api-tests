package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import post.PostCalls;
import services.DuckIdService;
import services.Validator;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class DeleteTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test
    @CitrusTest
    public void successfulDeleteDuck(@Optional @CitrusResource TestCaseRunner runner){
        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);
        String duckId = DuckIdService.getDuckId(runner, URL);
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", duckId)
        );

        Validator.validateSingleMessageResponse(runner, "message", "Duck is deleted", HttpStatus.OK, URL);
    }
}
