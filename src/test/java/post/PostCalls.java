package post;

import com.consol.citrus.TestCaseRunner;
import org.springframework.http.MediaType;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class PostCalls {

    static public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState, String URL) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .post("/api/duck/create")
                        .message()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(("{" +
                                "\"color\": \"" + color + "\"," +
                                "\"height\": " + height + "," +
                                "\"material\": \"" + material + "\"," +
                                "\"sound\": \"" + sound + "\"," +
                                "\"wingsState\": \"" + wingsState + "\"" +
                                "}"))
        );
    }
}
