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

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class UpdateTests extends TestNGCitrusSpringSupport {

    public static final String URL = "http://localhost:2222";

    @Test(description = "Проверка обновления у уточки цвета и высоты")
    @CitrusTest
    public void successfulChangeOfColorAndHeight(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("color", "yellow");
        runner.variable("height", "0.06");

        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);

        String duckId = DuckIdService.getDuckId(runner, URL);

        runner.$(
                http()
                        .client(URL)
                        .send()
                        .put("/api/duck/update")
                        .queryParam("color", "${color}")
                        .queryParam("height", "${height}")
                        .queryParam("id", duckId)
                        .queryParam("material", "rubber")
                        .queryParam("sound","quack")
                        .queryParam("wingsState", "FIXED")
        );

        Validator.validateSingleMessageResponse(runner, "message", "Duck with id = " + duckId + " is updated", HttpStatus.OK, URL);
        GetCalls.duckProperties(runner, duckId, URL);

        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .validate(jsonPath().expression("$.color", "${color}"))
                        .validate(jsonPath().expression("$.height", 6.0))
        );
    }

    @Test(description = "Проверка обновления у уточки цвета и звука")
    @CitrusTest
    public void successfulChangeOfColorAndSound(@Optional @CitrusResource TestCaseRunner runner){
        runner.variable("color", "yellow");
        runner.variable("sound", "woof");

        PostCalls.createDuck(runner, "red", 0.03, "rubber", "quack", "FIXED", URL);

        String duckId = DuckIdService.getDuckId(runner, URL);

        runner.$(
                http()
                        .client(URL)
                        .send()
                        .put("/api/duck/update")
                        .queryParam("color", "${color}")
                        .queryParam("height", "0.03")
                        .queryParam("id", duckId)
                        .queryParam("material", "rubber")
                        .queryParam("sound","${sound}")
                        .queryParam("wingsState", "FIXED")
        );

        Validator.validateSingleMessageResponse(runner, "message", "Duck with id = " + duckId + " is updated", HttpStatus.OK, URL);
        GetCalls.duckProperties(runner, duckId, URL);

        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .validate(jsonPath().expression("$.color", "${color}"))
                        .validate(jsonPath().expression("$.sound", "${sound}"))
        );
    }
}
