package web.expedite.smoke.broker;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.configWeb.GlobalGenerateName;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WES036_BrokerEdit {

    // Click Up:
    // CRM EXPEDITE - Smoke - Brokers
    // 2. Редактирование брокера

    // Global data
    String globalMC = GlobalGenerateName.globalMC();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void edit() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for creating a Broker
        final String atMcNumber = globalMC;
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

        // [Main Broker] table. Edit Broker
        $("#brokerssearch-mc_number").shouldBe(visible, EXPECT_GLOBAL).setValue(atMcNumber).pressEnter();
        SelenideElement rowBroker = $$("table.table-hover tbody tr").get(0).shouldHave(text(atMcNumber));
        rowBroker.shouldHave(text(atMcNumber));
        rowBroker.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowBroker.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        ElementsCollection dropDownBroker = rowBroker.$$(".dropdown-menu-right li");
        dropDownBroker.findBy(exactText("Update  Profile")).click();

        // [Update Broker] tab General. Check Broker after creation
        $("#update_broker").shouldBe(visible, Duration.ofSeconds(20));
        $("#brokers-mc_number").shouldHave(value(atMcNumber));
        $("#brokers-dba_name").shouldHave(value(atDbaName));
        $("#brokers-name").shouldHave(value(atLegalName));
        $("#brokers-entity_type").getSelectedOption().shouldHave(Condition.text(atEntityType));
        $("#brokers-address").shouldHave(value(atAddress));
        $("#brokers-second_mc_number").shouldHave(value(atFfName));
        $("#brokers-city").shouldHave(value(atCity));
        $("#brokers-note").shouldHave(text(atNote));

        // [Update Broker] tab Contact. Check Broker after creation
        $$(".nav-tabs li").findBy(text("Contact")).click();
        $("#brokers-main_name").shouldHave(value (atMainName));
        $("#brokers-main_phone_number").shouldHave(value(atMainPhoneNumber));
        $("#brokers-main_email").shouldHave(value(atMail));

        // [Update Broker] tab Agents. Check Broker after creation
        $$(".nav-tabs li").findBy(text("Agents")).click();
        $("#agents-0-name").shouldHave(value(atAgentName));
        $("#agents-0-last_name").shouldHave(value(atAgentLastName));
        $("#agents-0-phone_number").shouldHave(value(globalPhoneNumber + " ex 01"));
        $("#agent-cell_phone-update0").shouldHave(value(globalPhoneNumber + " ex 02"));

        // Data for edit a Broker
        GlobalGenerateName.globalName();
        final String dbaNameEdit = globalName + "DBA Name 2";
        final String legalNameEdit = globalName + "Legal Name 2";
        final String entityTypeEdit = "BROKER";
        final String addressEdit = "Mountain 2";
        final String cityEdit = "Colorado 2";
        final String noteEdit = globalName + "Note 2";
        final String mainNameEdit = globalName + "Main Name 2";
        final String mailEdit = globalMail;
        final String mainPhoneNumberEdit = globalPhoneNumber;
        final String ffNameEdit = globalName + "2nd MC/FF Name 2";
        final String agentNameEdit = globalName + "Agent Name 2";
        final String agentLastNameEdit = globalName + "Agent Last Name 2";
        final String agentMailEdit = "AgentMail_" + globalMail;
        final String agentPhoneNumberEdit = globalPhoneNumber + "01";
        final String agentCellPhoneEdit = globalPhoneNumber + "02";

        // [Update Broker] tab General. Edit data
        $$(".nav-tabs li").findBy(text("General")).click();
        $("#brokers-dba_name").setValue(dbaNameEdit);
        $("#brokers-name").setValue(legalNameEdit);
        $("#brokers-entity_type").shouldBe(visible).selectOption(entityTypeEdit);
        $("#brokers-second_mc_number").setValue(ffNameEdit);
        $("#brokers-note").setValue(noteEdit);
        $("#brokers-city").setValue(cityEdit);
        $("#brokers-address").setValue(addressEdit);

        // [Update Broker] hidden warning block
        SelenideElement warning = $(".field-brokers-address .warning-block");
        executeJavaScript("arguments[0].style.display='none';", warning);

        // [Update Broker] tab Contact. Edit data
        $$(".nav-tabs li").findBy(text("Contact")).click();
        $("#brokers-main_name").shouldBe(visible).setValue(mainNameEdit);
        $("#brokers-main_phone_number").setValue(mainPhoneNumberEdit);
        $("#brokers-main_email").setValue(mailEdit);

        // [Update Broker] tab Agents. Edit data
        $$(".nav-tabs li").findBy(text("Agents")).click();
        $(".edit_agent_btn").click();
        $("#agents-0-name").setValue(agentNameEdit);
        $("#agents-0-last_name").setValue(agentLastNameEdit);
        $("#agents-0-email").setValue(agentMailEdit);
        $("#agents-0-phone_number").setValue(agentPhoneNumberEdit);
        $("#agent-cell_phone-update0").setValue(agentCellPhoneEdit);

        // [Update Broker] button Submit
        SelenideElement updateModal = $("#update_broker");
        $("#update_broker_send").shouldBe(visible, enabled).click();
        updateModal.shouldNotBe(visible, EXPECT_GLOBAL);

        // [Main Broker] table. Edit Broker
        $("#brokerssearch-mc_number").shouldBe(visible, EXPECT_GLOBAL).setValue(atMcNumber).pressEnter();
        SelenideElement rowBrokerEdit = $$("table.table-hover tbody tr").get(0).shouldHave(text(atMcNumber));
        rowBrokerEdit.shouldHave(text(atMcNumber));
        rowBrokerEdit.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowBrokerEdit.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        ElementsCollection dropDownBrokerEdit = rowBrokerEdit.$$(".dropdown-menu-right li");
        dropDownBroker.findBy(exactText("Update  Profile")).click();

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Broker successfully updated"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // [Update Broker] tab General. Check Broker after edit
        $("#update_broker").shouldBe(visible, Duration.ofSeconds(20));
        $("#brokers-mc_number").shouldHave(value(atMcNumber));
        $("#brokers-name").shouldHave(value(legalNameEdit));
        $("#brokers-dba_name").shouldHave(value(dbaNameEdit));
        $("#brokers-entity_type").getSelectedOption().shouldHave(Condition.text(entityTypeEdit));
        $("#brokers-address").shouldHave(value(addressEdit));
        $("#brokers-second_mc_number").shouldHave(value(ffNameEdit));
        $("#brokers-city").shouldHave(value(cityEdit));
        $("#brokers-note").shouldHave(text(noteEdit));

        // [Update Broker] tab Contact. Check Broker after edit
        $$(".nav-tabs li").findBy(text("Contact")).click();
        $("#brokers-main_name").shouldHave(value(mainNameEdit));
        $("#brokers-main_phone_number").shouldHave(value(mainPhoneNumberEdit));
        $("#brokers-main_email").shouldHave(value(mailEdit));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}