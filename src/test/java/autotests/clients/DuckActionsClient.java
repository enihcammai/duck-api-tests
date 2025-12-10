package autotests.clients;

import autotests.EndpointConfig;
import autotests.payloads.DuckProperties;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.MessageType;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    @Qualifier("duckService")
    protected HttpClient duckService;


    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id)
        );
    }

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id)
        );
    }

    public void duckQuack(TestCaseRunner runner, String id, String repCount, String soundCount) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/quack")
                        .queryParam("id", id)
                        .queryParam("repetitionCount", repCount)
                        .queryParam("soundCount", soundCount)
        );
    }

    public void getDuckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id)
        );
    }

    public void createDuck(TestCaseRunner runner, DuckProperties properties) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(properties, new ObjectMapper()))
        );
    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id)
        );
    }

    public void updateDuck(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .put("/api/duck/update")
                        .queryParam("color", color)
                        .queryParam("height", height)
                        .queryParam("id", id)
                        .queryParam("material", material)
                        .queryParam("sound", sound)
                        .queryParam("wingsState", wingsState)
        );
    }

    public void validateFullBodyResponse(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState, HttpStatus responseCode) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(responseCode)
                        .message()
                        .type(MessageType.JSON)
                        .validate(jsonPath().expression("$.id", "@isNumber()@"))
                        .validate(jsonPath().expression("$.color", color))
                        .validate(jsonPath().expression("$.height", height))
                        .validate(jsonPath().expression("$.material", material))
                        .validate(jsonPath().expression("$.sound", sound))
                        .validate(jsonPath().expression("$.wingsState", wingsState))
        );
    }

    //    Нужна доработка
    public void validatePayload(TestCaseRunner runner, Object response, HttpStatus responseCode) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(responseCode)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(response, new ObjectMapper()))
        );
    }


    public void validateEmptyBodyResponseWithString(TestCaseRunner runner, HttpStatus responseCode) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(responseCode)
                        .message()
                        .type(MessageType.JSON)
                        .body("{}")
        );
    }

    public void validateResources(TestCaseRunner runner, String expectedPayload, HttpStatus responseCode) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(responseCode)
                        .message()
                        .type(MessageType.JSON)
                        .body(new ClassPathResource(expectedPayload))
        );
    }

    public String getDuckId(TestCaseRunner runner, TestContext context) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .extract(fromBody().expression("$.id", "duckId"))
        );

        return context.getVariable("${duckId}");
    }


}

