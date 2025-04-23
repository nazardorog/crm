package web.simple;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;

public class ExampleTest {

    public class WebDriverConfig {
        public static void setup() {
            String remoteUrl = System.getenv().getOrDefault("SELENIUM_REMOTE_URL", "http://localhost:4444/wd/hub");
            Configuration.remote = remoteUrl;

            Configuration.browser = "chrome";
            Configuration.headless = true;

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");
            Configuration.browserCapabilities = capabilities;
        }
    }


    @Test
    public void openPageTest() {
        // Налаштовуємо WebDriver для підключення до Selenium
        WebDriverConfig.setup();
//        WebDriverConfig.setup();

        // Відкриваємо веб-сторінку
        open("https://www.google.com");

        // Перевіряємо, чи сторінка успішно відкрилася
        assert title().contains("Google");
    }
}
