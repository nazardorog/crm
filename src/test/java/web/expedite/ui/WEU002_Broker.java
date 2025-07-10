package web.expedite.ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;

import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Epic("Логістика")
@Feature("Створення вантажу з файлом BOL")
public class WEU002_Broker {

    @Test
    @Story("Основний сценарій створення")
    public void broker() {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();
        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Brokers"));

        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Brokers")).shouldBe(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
