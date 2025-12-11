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
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected SingleConnectionDataSource testDb;

    @Autowired
    @Qualifier("duckService")
    protected HttpClient duckService;


    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    @Step("Уточка плывёт")
    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id)
        );
    }

    @Step("Уточка летит")
    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id)
        );
    }

    @Step("Уточка крякает")
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

    @Step("Получаем свойства уточки")
    public void getDuckProperties(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id)
        );
    }

    @Step("Создаём уточку")
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

    @Step("Удаляем уточку")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(
                http()
                        .client(duckService)
                        .send()
                        .delete("/api/duck/delete")
                        .queryParam("id", id)
        );
    }

    @Step("Изменяем уточку")
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

    @Step("Проверяем полное тело ответа")
    public void validateFullBodyResponse(TestCaseRunner runner, String id, String color, double height, String material, String sound, String wingsState, HttpStatus responseCode) {
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


    @Step("Проверяем ответ с помощью Payload")
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

    @Step("Проверяем ответ с помощью базы данных")
    protected void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height,
                                          String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR", color)
                .validate("HEIGHT", height)
                .validate("MATERIAL", material)
                .validate("SOUND", sound)
                .validate("WINGS_STATE", wingsState));
    }

    @Step("Проверяем пустое тело ответа")
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

    @Step("Проверяем с помощью файлов с ресурсами")
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

    @Step("Получаем id уточки")
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


    @Step("Получаем переменную с id уточки")
    public void getDuckId1(TestCaseRunner runner) {
        runner.$(
                http()
                        .client(duckService)
                        .receive()
                        .response(HttpStatus.OK)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .extract(fromBody().expression("$.id", "duckId"))
        );

    }
}

