package utilsWeb.jenkins;

import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.internal.shadowed.jackson.core.type.TypeReference;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.json.TypeToken;
import utilsMobile.configMobile.GlobalLoginMob;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomName {

    public static JSONObject description;

    public static JSONObject description() {

        // Read JSON
        try {
            String content = new String(GlobalLoginMob.class.getClassLoader().getResourceAsStream("json/descriptionsTest.json").readAllBytes());
            description = new JSONObject(content);
            return description;
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Ошибка загрузки credentials.json: " + e.getMessage());
        }
    }

    public static String getDescription() {
        if (description == null) {
            description();
        }

        // Отримує імя класу запущеного тесту
        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);

        String desc = description.getString(className);
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(desc));

        return desc;
    }

}
