package web.expedite.ui;

import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import utilsWeb.commonWeb.*;

import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

@Listeners(utilsWeb.commonWeb.Listener.class)
@Epic("Expedite")
@Feature("Smoke")
public class WEU002_Broker {

    @Test(testName = "Створення брокера")
    @Epic("Load board")
    @Story("Создание Брокера")
    public void broker() {

        // Встановлюємо кастомну назву для тесту
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Создание Брокера");
        });

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
