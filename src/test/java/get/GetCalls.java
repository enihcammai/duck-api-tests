package get;

import com.consol.citrus.TestCaseRunner;

import static com.consol.citrus.http.actions.HttpActionBuilder.http;

public class GetCalls {

    static public void duckSwim(TestCaseRunner runner, String id, String URL) {
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .get("/api/duck/action/swim")
                        .queryParam("id", id)
        );
    }

    static public void duckFly(TestCaseRunner runner, String id, String URL){
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .get("/api/duck/action/fly")
                        .queryParam("id", id)
        );
    }

    static public void duckQuack(TestCaseRunner runner, String id, String repCount, String soundCount, String URL){
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .get("/api/duck/action/quack")
                        .queryParam("id", id)
                        .queryParam("repetitionCount", repCount)
                        .queryParam("soundCount", soundCount)
        );
    }

    static public void duckProperties(TestCaseRunner runner, String id, String URL){
        runner.$(
                http()
                        .client(URL)
                        .send()
                        .get("/api/duck/action/properties")
                        .queryParam("id", id)
        );
    }
}
