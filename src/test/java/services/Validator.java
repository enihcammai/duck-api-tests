package services;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.message.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static com.consol.citrus.validation.DelegatingPayloadVariableExtractor.Builder.fromBody;

public class Validator {
    static public void validateSingleMessageResponse(TestCaseRunner runner, String responseKey, String responseValue, HttpStatus responseCode, String URL) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(responseCode)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{\"" + responseKey + "\": \"" + responseValue + "\"}")
        );
    }

    static public void validateFullBodyResponse(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState, HttpStatus responseCode, String URL) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(responseCode)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body("{" +
                                "\"color\": \"" + color + "\"," +
                                "\"height\": " + height + "," +
                                "\"material\": \"" + material + "\"," +
                                "\"sound\": \"" + sound + "\"," +
                                "\"wingsState\": \"" + wingsState + "\"" +
                                "}")
        );
    }

    static public void validateCreate(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState, String URL, HttpStatus responseCode) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(responseCode)
                        .message()
                        .type(MessageType.JSON)
                        .extract(fromBody().expression("${id}", "duckId"))
                        .body("{" +
                                "\"id\": \"" + "${duckId}" + "\"," +
                                "\"color\": \"" + color + "\"," +
                                "\"height\": " + height + "," +
                                "\"material\": \"" + material + "\"," +
                                "\"sound\": \"" + sound + "\"," +
                                "\"wingsState\": \"" + wingsState + "\"" +
                                "}")
        );
    }

}
