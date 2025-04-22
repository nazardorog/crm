package web.expedite.ui;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

@Listeners({AllureTestNg.class})
public class TestCase2Interface {//extends Login {

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Step("Логін користувача")
    public void numberLoads() {

        web.config.WebDriverConfig.setup();
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#show_my_loads").setSelected(true);

        $(".content-header").shouldHave(text("Load Board"));
        $(".li-tabs-home.li-tabs-route .updated-tabs-name-link").shouldHave(text("Loads en Route"));

        $(".li-tabs-home.li-tabs-delivered .updated-tabs-name-link").shouldHave(text("Loads Delivered"));
        $("#pjaxContentDeliveredCount").shouldHave(text("0"));
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

        System.out.println("TestCase2Interface - Test Pass");
    }
}
