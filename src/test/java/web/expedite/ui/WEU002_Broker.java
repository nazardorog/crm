package web.expedite.ui;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalConfig;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WEU002_Broker {

    @Test
    public void broker() {

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

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
