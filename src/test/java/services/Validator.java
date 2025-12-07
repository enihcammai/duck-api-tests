package services;

import com.consol.citrus.TestCaseRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.consol.citrus.dsl.JsonPathSupport.jsonPath;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

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

    static public void validateFullBodyResponse(TestCaseRunner runner, String responseKey, String responseValue, HttpStatus responseCode, String URL) {
        runner.$(
                http()
                        .client(URL)
                        .receive()
                        .response(responseCode)
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .validate(jsonPath().expression("$." + responseKey, responseValue))
        );
    }

}
