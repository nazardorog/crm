package web.expedite.ui;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WEU007_Load {

    @Test
    public void load() {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".loads-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".loads-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Loads"));
        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Loads")).shouldBe(visible);

        $("label").shouldHave(text("Show my loads"));
        $("input[name='show_my_loads_ch']").shouldBe(visible).shouldNotBe(checked);

        $$("th").findBy(text("#")).shouldBe(visible);
        $$("th").findBy(text("Pro#")).shouldBe(visible);
        $$("th").findBy(text("Truck")).shouldBe(visible);
        $$("th").findBy(text("Driver")).shouldBe(visible);
        $$("th").findBy(text("Customer")).shouldBe(visible);
        $$("th").findBy(text("Miles")).shouldBe(visible);
        $$("th").findBy(text("Pick")).shouldBe(visible);
        $$("th").findBy(text("Rate")).shouldBe(visible);
        $$("th").findBy(text("Status")).shouldBe(visible);
        $$("th").findBy(text("Canceled load")).shouldBe(visible);
        $$("th").findBy(text("Actions")).shouldBe(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
