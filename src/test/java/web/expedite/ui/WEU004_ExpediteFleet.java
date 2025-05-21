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

public class WEU004_ExpediteFleet {

    @Test
    public void expediteFleet() {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".expedite-fleet-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".expedite-fleet-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Expedite Fleet"));

        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Expedite Fleet")).shouldBe(visible);

        $("label").shouldHave(text("Hide OOS Trucks"));
        $(".available-filter").shouldHave(text("View map trucks"));
        $$("td").findBy(text("Truck Number")).shouldBe(visible);
        $$("td").findBy(text("Status")).shouldBe(visible);
        $$("td").findBy(text("Date When Will Be There")).shouldBe(visible);
        $$("td").findBy(text("Driver Name")).shouldBe(visible);
        $$("td").findBy(text("Email/Phone")).shouldBe(visible);
        $$("td").findBy(text("City State Zip")).shouldBe(visible);
        $$("td").findBy(text("Dimensions/ Payload")).shouldBe(visible);
        $$("td").findBy(text("Hold Time")).shouldBe(visible);
        $$("td").findBy(text("Hold Actions")).shouldBe(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
