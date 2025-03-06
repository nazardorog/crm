package Web.Interface;

import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class TestCase2Interface {

    @Test(dependsOnMethods = {"Web.Login.loginWeb"})
    public void numberLoads() {

        $(".logo-mini-icon").click();
        $("#show_my_loads").setSelected(true);

        $(".li-tabs-home.li-tabs-route .updated-tabs-name-link").shouldHave(text("Loads en Route"));
//        $("#pjaxContentTrucksRouteCount").shouldHave(text("0"));
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

        System.out.println("Test2 - OK");
    }
}
