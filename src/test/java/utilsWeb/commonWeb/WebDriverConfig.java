package utilsWeb.commonWeb;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import utilsWeb.configWeb.GlobalConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.webdriver;

public class WebDriverConfig {

    public static void setup() {

        String runEnv = System.getenv().getOrDefault("RUN_ENV", "local");

        Configuration.browser = "chrome";
        Configuration.headless = true;
        Configuration.reportsFolder = "allure-results";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        Configuration.browserCapabilities = capabilities;
        Configuration.downloadsFolder = System.getProperty("downloads.folder", "C:/Empire");
        // Configuration.downloadsFolder = System.getProperty("downloads.folder", "C:/Users/User217/Documents/auto_test/crm-test/empire");
        // Configuration.downloadsFolder = System.getProperty("downloads.folder", "/Users/admin/Documents/Work/crm-test/empire");

        if (runEnv.equals("remote")) {
            Configuration.remote = System.getenv().getOrDefault("SELENIUM_REMOTE_URL", "http://localhost:4444/wd/hub");
            Configuration.headless = true; // без GUI
        } else {
            Configuration.headless = false; // для дебагу
        }

        Allure.step("Відкриває браузер", () ->
                Selenide.open(GlobalConfig.WEB_SITE));

        WebDriver driver = webdriver().driver().getWebDriver();
        driver.manage().window().maximize();
    }
}
