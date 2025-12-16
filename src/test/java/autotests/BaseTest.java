package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.context.TestContext;
import com.consol.citrus.http.actions.HttpClientRequestActionBuilder;
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
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import java.util.Map;

import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {

    @Autowired
    @Qualifier("duckService")
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;


    protected void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient URL, String path, String queryName, String queryValue) {
        runner.$(http()
                .client(URL)
                .send()
                .get(path)
                .queryParam(queryName, queryValue));
    }

    protected void sendMultiParamGetRequest(TestCaseRunner runner, HttpClient URL, String path, Map<String, String> queryParams){
        HttpClientRequestActionBuilder actionBuilder = http()
                .client(URL)
                .send()
                .get(path);

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            actionBuilder = actionBuilder.queryParam(entry.getKey(), entry.getValue());
        }

        runner.$(actionBuilder);
    }

    protected void sendPostRequest(TestCaseRunner runner, HttpClient URL, String path, Object payload) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .post(path)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(new ObjectMappingPayloadBuilder(payload, new ObjectMapper()))
        );
    }

    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient URL, String path, String queryName, String queryValue){
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .delete(path)
                        .queryParam(queryName, queryValue)
        );
    }

    protected void sendFlexibleParamUpdateRequest(TestCaseRunner runner, HttpClient URL, String path, Map<String, String> queryParams){
        HttpClientRequestActionBuilder actionBuilder = http()
                .client(URL)
                .send()
                .put(path);

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            actionBuilder = actionBuilder.queryParam(entry.getKey(), entry.getValue());
        }

        runner.$(actionBuilder);
    }

    protected void validatePayload(TestCaseRunner runner, Object response, HttpStatus responseCode) {
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

    protected void validateResources(TestCaseRunner runner, String expectedPayload, HttpStatus responseCode) {
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

    protected void extractId(TestCaseRunner runner) {
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

    protected String getDuckId(TestCaseRunner runner, TestContext context) {
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
