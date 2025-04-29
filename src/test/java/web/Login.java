package web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;

public class Login {

    public String webSite = "https://preprod.empirenational.com/adm";

    @BeforeMethod
    @Description("Авторизація користувача в системі")
    public void loginWeb() throws InterruptedException {

        Configuration.browser = "chrome";
        Configuration.reportsFolder = "allure-results";
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        System.setProperty("webdriver.chrome.driver", "C:/automation/chromedriver-win64/135/chromedriver.exe");
        Allure.step("Відкриває браузер", () ->
                Selenide.open(webSite));

                WebDriver driver = webdriver().driver().getWebDriver();
                driver.manage().window().maximize();

        Allure.step("Авторизація користувача", () -> {
                    Allure.step("Вводить логін", () ->
                            $("#loginform-username").setValue("test1te"));

                    Allure.step("Вводить пароль", () ->
                            $("#loginform-password").setValue("t34n2215P392"));

                    Allure.step("Клік по кнопці Submit", () ->
                            $(".btn.btn-primary.btn-block.btn-flat").click());
                });
        Thread.sleep(1000);
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
