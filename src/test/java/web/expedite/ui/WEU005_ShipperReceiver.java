package web.expedite.ui;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WEU005_ShipperReceiver {

    @Test
    public void shipperReceiver() {

        // Login
        GlobalLogin.login("exp_disp1");

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
