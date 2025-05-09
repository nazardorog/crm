package web.expedite.ui;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalConfig;

import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WEU005_ShipperReceiver {

    @Test
    public void shipperReceiver() {

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".shippers-receivers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Shippers Receivers"));

        $(".shippers-receivers-index").shouldHave(text("Create Shippers Receivers"));
        $$("th").findBy(text("#")).shouldBe(visible);
        $$("th").findBy(text("Name")).shouldBe(visible);
        $$("th").findBy(text("location")).shouldBe(visible);
        $$("th").findBy(text("Street 1")).shouldBe(visible);
        $$("th").findBy(text("Street 2")).shouldBe(visible);
        $$("th").findBy(text("Lat")).shouldBe(visible);
        $$("th").findBy(text("Lng")).shouldBe(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
