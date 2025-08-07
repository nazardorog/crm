package utilsWeb.jenkins;

import com.google.gson.Gson;
import io.qameta.allure.Allure;
import io.qameta.allure.internal.shadowed.jackson.core.type.TypeReference;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.openqa.selenium.json.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomName {

    private static final Map<String, String> testNames = new HashMap<>();
    private static Map<String, String> descriptions;

    static {
        testNames.put("WES001_LoadCreateBol", "Создание New Load с типом файла Bol");
        testNames.put("WES002_LoadCreateRateConfirmation", "Создание New Load с типом файла Rate Confirmation");
        testNames.put("WES003_LoadCreatePod", "Создание New Load с типом файла Pod");
        testNames.put("WES004_LoadCreateOther", "Создание New Load с типом файла Other");
        testNames.put("WES005_ValidateRateBrokerOwner", "Создание New Load / валидация полей рейта брокер и драйвера");
        testNames.put("WES006_ValidateDateShipperReceiver", "Создание New Load / валидация полей даты в ПУ и ДЕЛ");

    }

    public static void setCustomTestName() {

        String fullClassName = Thread.currentThread().getStackTrace()[2].getClassName();
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String displayName = testNames.getOrDefault(className, className);
        Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(displayName));
    }



    static {
        loadDescriptions();
    }

    public static void loadDescriptions() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            descriptions = mapper.readValue(
                    new File("test_descriptions.json"),
                    new TypeReference<>() {}
            );
        } catch (IOException e) {
            e.printStackTrace();
            descriptions = Collections.emptyMap(); // fallback
        }
    }

    public static String getCustomName(String className) {
        return descriptions.getOrDefault(className, className);
    }


}
