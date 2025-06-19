package utilsWeb.configWeb;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class GlobalLogin {

    private static JSONObject credentials;

    public static void openWeb(String user) {

        String runEnv = System.getenv().getOrDefault("RUN_ENV", "local");

        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.reportsFolder = "allure-results";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        Configuration.browserCapabilities = capabilities;
        Configuration.browserSize = "1920x1080";
        Configuration.downloadsFolder = GlobalConfig.dotenv.get("FILES_PATH");
        Configuration.baseUrl = GlobalConfig.dotenv.get("WEB_SITE");

        if (runEnv.equals("remote")) {
            Configuration.remote = System.getenv().getOrDefault("SELENIUM_REMOTE_URL", "http://selenium-hub:4444/wd/hub");
            Configuration.headless = true; // без GUI
        } else {
            Configuration.headless = false; // для дебагу
        }

        Allure.step("Відкриває браузер", () ->
                Selenide.open(Configuration.baseUrl));

        WebDriver driver = webdriver().driver().getWebDriver();

    }

    @Description("Авторизація користувача в системі")
    public static void login(String user) {

        // Відкрити браузер
        credentials();
        openWeb(user);

        // Дані авторизації
        String login = getUsername(user);
        String password = getPassword(user);

        // Авторизація
        Allure.step("Авторизація користувача", () -> {
            Allure.step("Вводить логін", () ->
                    $("#loginform-username").setValue(login));

            Allure.step("Вводить пароль", () ->
                    $("#loginform-password").setValue(password));

            Allure.step("Клік по кнопці Submit", () ->
                    $(".btn.btn-primary.btn-block.btn-flat").click());
//
//            Allure.step("Очікує відкриття Load board", () ->
//                    $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL));
        });
    }

    @Description("Вихід з акаунта")
    public static void logout () {

        $(".user-image-profile").shouldBe(clickable).click();
        $(".user-menu").shouldHave(cssClass("open"), EXPECT_GLOBAL);
        $(".exit-user-block").shouldBe(visible, EXPECT_GLOBAL).click();
    }

    public static void credentials() {

        // Read JSON
        try {
            String content = new String(GlobalLogin.class.getClassLoader().getResourceAsStream("json/credentials.json").readAllBytes());
            credentials = new JSONObject(content);
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Ошибка загрузки credentials.json: " + e.getMessage());
        }
    }

    public static String getUsername (String user){
        return credentials.getJSONObject(user).getString("username");
    }

    public static String getPassword (String user){
        return credentials.getJSONObject(user).getString("password");
    }

    public static String getWebSite (){
        return credentials.getString("webSite");
    }

    public static String uploadFolder (){
        return credentials.getString("uploadFolder");
    }
}
