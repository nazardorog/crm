package web.expedite.smoke.loads;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalLogin;
import utilsWeb.createDataExp.WCE005_Load;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES062_LoadsDispatchCheck {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loads
    // 2. Pro# и глазик

    // Global data
    WCE005_Load loadCreate = new WCE005_Load();

    @Test
    public void dispatchCheck() {

        // Create new Loads
        WCE005_Load load = loadCreate.create();

        // Login
        GlobalLogin.login("exp_disp1");

        // Data
        final String atDispatcher = "Mary Miller";

        // [Sidebar] Go to Main Loads
        $(".loads-user").hover();
        $(".loads-user").click();
        $("body").click();

        // [Loads] Table. Eye open
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(load.loadNumber).pressEnter();
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0).shouldHave(text(load.loadNumber), EXPECT_GLOBAL);
        rowTable.shouldHave(text(load.loadNumber));
        rowTable.$(".glyphicon-eye-open").click();

        // [Dispatch] Main Info. Broker table check data
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);
        $(".table-bordered").shouldHave(text(load.atBroker));
        $(".view_pick_up_location").shouldHave(text(load.atShippersOriginName));
        $(".view_delivery_location").shouldHave(text(load.atShippersDistName));

        // [Dispatch] Main Info. Broker's Rate check data
        ElementsCollection expenses = $$(".broker-rate-block-flex");
        expenses.findBy(text("$0.00"));
        expenses.findBy(exactText("$1,000.00"));

        // [Dispatch] Main Info. Driver table check data
        SelenideElement rowDriver = $$(".table-modal-get-drivers tbody tr").findBy(text(load.atDriver));
        rowDriver.shouldHave(text(load.atDriver));
        rowDriver.shouldHave(text(load.atTeamDriver));
        rowDriver.shouldHave(text(load.atOwner));
        rowDriver.shouldHave(text(load.atTruck));
        rowDriver.shouldHave(text("$0.00"));

        // [Dispatch] Main Info. Table Dispatchers check data
        SelenideElement rowCheckCalls = $$("table.table-dispatch-check-calls tbody tr").findBy(text(atDispatcher));
        rowCheckCalls.$(".check_call_note").shouldHave(text("Drivers' dispatched"));

        // [Dispatch] Button Close
        $("#view_load .close").click();

        // [Loads] Table. Number loads open
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(load.loadNumber).pressEnter();
        SelenideElement rowTableAfter = $$("table.table-striped tbody tr").get(0).shouldHave(text(load.loadNumber), EXPECT_GLOBAL);
        rowTableAfter.$$("a.view_load").findBy(text(load.loadNumber)).click();

        // [Dispatch] Main Info. Broker table check data
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);
        $(".table-bordered").shouldHave(text(load.atBroker));
        $(".view_pick_up_location").shouldHave(text(load.atShippersOriginName));
        $(".view_delivery_location").shouldHave(text(load.atShippersDistName));

        // [Dispatch] Main Info. Broker's Rate check data
        ElementsCollection expensesAfter = $$(".broker-rate-block-flex");
        expensesAfter.findBy(text("$0.00"));
        expensesAfter.findBy(exactText("$1,000.00"));

        // [Dispatch] Main Info. Driver table check data
        SelenideElement rowDriverAfter = $$(".table-modal-get-drivers tbody tr").findBy(text(load.atDriver));
        rowDriverAfter.shouldHave(text(load.atDriver));
        rowDriverAfter.shouldHave(text(load.atTeamDriver));
        rowDriverAfter.shouldHave(text(load.atOwner));
        rowDriverAfter.shouldHave(text(load.atTruck));
        rowDriverAfter.shouldHave(text("$0.00"));

        // [Dispatch] Main Info. Table Dispatchers check data
        SelenideElement rowCheckCallsAfter = $$("table.table-dispatch-check-calls tbody tr").findBy(text(atDispatcher));
        rowCheckCallsAfter.$(".check_call_note").shouldHave(text("Drivers' dispatched"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
