package web.expedite.smoke.broker;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.configWeb.GlobalGenerateName;
import utilsWeb.configWeb.GlobalLogin;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WES037_BrokerDnuAdd {

    // Click Up:
    // CRM EXPEDITE - Smoke - Brokers
    // 3. Change DNU category

    // Global data
    String globalNumberSeven = GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void dnuAdd() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for creating a Broker
        final String atMcNumber = globalNumberSeven;
        final String atDbaName = globalName + "DBA Name";
        final String atLegalName = globalName + "Legal Name";
        final String atEntityType = "BROKER";
        final String atAddress = "Mountain";
        final String atCity = "Colorado";
        final String atNote = globalName + "Note";
        final String atMainName = globalName + "Main Name";
        final String atMail = globalMail;
        final String atMainPhoneNumber = globalPhoneNumber;
        final String atFfName = globalName + "2nd MC/FF Name";
        final String atAgentName = globalName + "Agent Name";
        final String atAgentLastName = globalName + "Agent Last Name";
        final String atAgentMail = "AgentMail_" + globalMail;
        final String atAgentPhoneNumber = globalPhoneNumber + "01";
        final String atAgentCellPhone = globalPhoneNumber + "02";

        // Open [New load]
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();
        $("#add_load").shouldBe(enabled, EXPECT_GLOBAL);

        // Open [Add Broker] with [New load]
        $("#new_broker").shouldBe(visible, EXPECT_GLOBAL).click();
        $("#add_broker").shouldBe(visible, EXPECT_GLOBAL);

        // [Add Broker] tab General
        $(byText("General")).parent().shouldHave(cssClass("active"));
        $("#brokers-mc_number").shouldBe(visible, EXPECT_GLOBAL).setValue(atMcNumber);
        $("#brokers-dba_name").setValue(atDbaName);
        $("#brokers-name").setValue(atLegalName);
        $("#brokers-entity_type").selectOption(atEntityType);
        $("#brokers-second_mc_number").setValue(atFfName);
        $("#brokers-address").setValue(atAddress);
        $("#brokers-city").setValue(atCity);
        $("#brokers-note").setValue(atNote);

        // [Add Broker] tab Contact
        $$(".nav-tabs li").findBy(text("Contact")).click();
        $("#brokers-main_name").setValue(atMainName);
        $("#brokers-main_phone_number").setValue(atMainPhoneNumber);
        $("#brokers-main_email").setValue(atMail);

        // [Add Broker] tab Agents
        $$(".nav-tabs li").findBy(text("Agents")).click();
        $("#agents-0-name").setValue(atAgentName);
        $("#agents-0-last_name").setValue(atAgentLastName);
        $("#agents-0-email").setValue(atAgentMail);
        $("#agents-0-phone_number").setValue(atAgentPhoneNumber);
        $("#agent-cell_phone-update0").setValue(atAgentCellPhone);

        // [Add Broker] button Submit
        $("#add_broker_send").shouldBe(clickable).click();
        $("#add_broker").shouldNotBe(visible, EXPECT_GLOBAL);

        // [New load] check the created broker
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $(".bt-load-broker-main-flex").shouldHave(text(atLegalName));
        $(".bt-load-broker-main-flex").shouldHave(text(atDbaName));
        $(".bt-load-broker-main-flex").shouldHave(text(atCity));
        $(".bt-load-broker-main-flex").shouldHave(text(atAddress));
        $(".bt-load-broker-main-flex").shouldHave(text(atMainPhoneNumber));

        // [New load] select Agent and check
        $$("select#loads-agent_id option").findBy(text(atAgentName)).click();
        $("#loads-agent_id").shouldHave(text(atAgentName + " " + atAgentLastName));

        // [New load] button Close
        $("#add_load .close").click();
        $("#add_load").shouldNotBe(visible, EXPECT_10);

        // [Sidebar] go to Main Brokers
        $(".brokers-user").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".brokers-user").click();
        $("body").click();

        // [Main Broker] table. Add to DNU
        $("#brokerssearch-mc_number").shouldBe(visible, EXPECT_GLOBAL).setValue(atMcNumber).pressEnter();
        SelenideElement rowBroker = $$("table.table-hover tbody tr").get(0).shouldHave(text(atMcNumber));
        rowBroker.shouldHave(text(atMcNumber));

        // [Main Broker] table. Check broker no Dnu
        rowBroker.shouldHave(text("Active"));

        // [Main Broker] open [Dnu]
        rowBroker.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowBroker.$(".btn-group").shouldHave(cssClass("open"),EXPECT_GLOBAL);
        ElementsCollection dropDownBroker = rowBroker.$$(".dropdown-menu-right li");
        dropDownBroker.findBy(exactText("Add to DNU")).click();
        $("#broker_dnu_modal").shouldBe(visible, EXPECT_GLOBAL);
        $("#broker_dnu_modal .modal-content-wrapper").shouldBe(visible, EXPECT_GLOBAL);

        // [Dnu] input data
        $("#category-dropdown").selectOption("4 - Dry Van Only");
        $("#category-dropdown").getSelectedOption().shouldHave(text("4 - Dry Van Only"),EXPECT_GLOBAL);
        $("#brokers-comment").shouldBe(visible, EXPECT_5).hover();
        $("#brokers-comment").setValue("DNU reason massage").shouldHave(enabled, EXPECT_GLOBAL).pressEnter();
        $("#brokers-comment").shouldHave(value("DNU reason massage"), EXPECT_GLOBAL);

        // [Dnu] button Submit
        $("#broker_from_dnu_send").click();
        $("#brokers-blacklist-form").shouldNotBe(visible, EXPECT_GLOBAL);

        // [Main Broker] table. Check broker Dnu
        $("#brokers-grid-view").shouldBe(visible, EXPECT_GLOBAL);
        rowBroker.shouldHave(text("DNU"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
