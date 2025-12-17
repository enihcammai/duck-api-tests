package autotests.clients;

import autotests.BaseTest;
import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import io.qameta.allure.Step;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends BaseTest {

    @Step("Уточка плывёт")
    public void duckSwim(TestCaseRunner runner, String id) {
        sendGetRequest(runner, duckService, "/api/duck/action/swim", "id", id);
    }

    @Step("Уточка летит")
    public void duckFly(TestCaseRunner runner, String id) {
        sendGetRequest(runner, duckService, "/api/duck/action/fly", "id", id);
    }

    @Step("Уточка крякает")
    public void duckQuack(TestCaseRunner runner, String id, String repCount, String soundCount) {
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        params.put("repetitionCount", repCount);
        params.put("soundCount", soundCount);

        sendMultiParamGetRequest(runner, duckService, "/api/duck/action/quack", params);
    }

    @Step("Получаем свойства уточки")
    public void getDuckProperties(TestCaseRunner runner, String id) {
        sendGetRequest(runner, duckService, "/api/duck/action/properties", "id", id);
    }

    @Step("Создаём уточку")
    public void createDuck(TestCaseRunner runner, Object payload) {
        sendPostRequest(runner, duckService, "/api/duck/create", payload);
    }

    @Step("Создаём уточку через БД")
    public Integer createDuckInDB(TestCaseRunner runner, String color, String height, String material, String sound, String wingsState) {
        Random random = new Random();
        int id = (int) System.currentTimeMillis() % 1000000 + random.nextInt(1000);
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (" + id + ", '" + color + "', " + height + ", '" + material + "', '" + sound + "','" + wingsState + "');");

        runner.variable("duckId", Integer.toString(id));

        return id;
    }

    @Step("Удаляем уточку")
    public void deleteDuck(TestCaseRunner runner, String id) {
        sendDeleteRequest(runner, duckService, "/api/duck/delete", "id", id);
    }

    @Step("Изменяем уточку")
    public void updateDuck(TestCaseRunner runner, String id, String color, String height, String material, String sound, String wingsState) {
        Map<String, String> params = new HashMap<>();
        params.put("color", color);
        params.put("height", height);
        params.put("id", id);
        params.put("material", material);
        params.put("sound", sound);
        params.put("wingsState", wingsState);

        sendFlexibleParamUpdateRequest(runner, duckService, "/api/duck/update", params);
    }
}

