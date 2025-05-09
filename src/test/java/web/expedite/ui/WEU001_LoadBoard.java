package web.expedite.ui;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import io.qameta.allure.*;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Listeners({AllureTestNg.class})
public class WEU001_LoadBoard {//{

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Step("Логін користувача")
    public void loadBoard() {

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#show_my_loads").setSelected(true);

        $(".content-header").shouldHave(text("Load Board"));
        $(".li-tabs-home.li-tabs-route .updated-tabs-name-link").shouldHave(text("Loads en Route"));

        $(".li-tabs-home.li-tabs-delivered .updated-tabs-name-link").shouldHave(text("Loads Delivered"));
        $("#pjaxContentDeliveredCount").shouldHave(text("1"));
        $(".li-tabs-home.li-tabs-issue .updated-tabs-name-link").shouldHave(text("Loads Issue"));
        $("#pjaxContentIssueCount").shouldHave(text("0"));
        $(".li-tabs-home.li-tabs-invoiced .updated-tabs-name-link").shouldHave(text("Loads Invoiced"));
        $("#pjaxContentInvoicedCount").shouldHave(text("0"));
        $(".li-tabs-home.li-tabs-paid .updated-tabs-name-link").shouldHave(text("Loads Paid"));
//        $("#pjaxContentPaidCount").shouldHave(text("0"));

        $(".li-tabs-home.li-tabs-route .updated-tabs-name-link").click();
        $(".li-tabs-home.li-tabs-delivered .updated-tabs-name-link").click();
        $(".li-tabs-home.li-tabs-issue .updated-tabs-name-link").click();
        $(".li-tabs-home.li-tabs-invoiced .updated-tabs-name-link").click();
        $(".li-tabs-home.li-tabs-paid .updated-tabs-name-link").click();

        $("#new_load").shouldHave(text("New Load"));
        $("#OTR").shouldHave(text("OTR"));
        $(".content-wrapper").shouldHave(text("Load Board"));

        $(".button-set-language").shouldHave(text("EN"));
        $(".hidden-xs").shouldHave(text("Mary Miller"));
        $(".breadcrumb").shouldHave(text("Home"));
        $(".active").shouldHave(text("Load Board"));
        $("label").shouldHave(text("Show my loads"));

        $$("th").findBy(text("Pro")).shouldBe(visible);
        $$("th").findBy(text("Truck")).shouldBe(visible);
        $$("th").findBy(text("Driver / Carrier")).shouldBe(visible);
        $$("th").findBy(text("Customer/Broker")).shouldBe(visible);
        $$("th").findBy(text("Destination")).shouldBe(visible);
        $$("th").findBy(text("Actions")).shouldBe(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
