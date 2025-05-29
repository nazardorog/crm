package web.expedite.smoke.broker;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES035_BrokerCreate {

    // Click Up:
    // CRM EXPEDITE - Smoke - Brokers
    // 1. Создание Брокера

    // Global data
    String globalNumberSeven = GlobalGenerateName.globalNumberSeven(); //7 digit
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void create() {

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

        // Create new load
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();
        $("#add_load").shouldBe(enabled, EXPECT_GLOBAL);

        // Відкриває створення Broker з New load
        $("#new_broker").shouldBe(visible, EXPECT_GLOBAL).click();
        $("#add_broker").shouldBe(visible, EXPECT_GLOBAL);

        // Фрейм Add Broker вкладка General
        $("#brokers-mc_number").shouldBe(visible, EXPECT_GLOBAL).setValue(atMcNumber);
        $("#brokers-dba_name").setValue(atDbaName);
        $("#brokers-name").setValue(atLegalName);
        $("#brokers-entity_type").selectOption(atEntityType);
        $("#brokers-second_mc_number").setValue(atFfName);
        $("#brokers-address").setValue(atAddress);
        $("#brokers-city").setValue(atCity);
        $("#brokers-note").setValue(atNote);

        // Фрейм Add Broker вкладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_name").setValue(atMainName);
        $("#brokers-main_phone_number").setValue(atMainPhoneNumber);
        $("#brokers-main_email").setValue(atMail);

        // Фрейм Add Broker вкладка Agents
        $(byText("Agents")).click();
        $("#agents-0-name").setValue(atAgentName);
        $("#agents-0-last_name").setValue(atAgentLastName);
        $("#agents-0-email").setValue(atAgentMail);
        $("#agents-0-phone_number").setValue(atAgentPhoneNumber);
        $("#agent-cell_phone-update0").setValue(atAgentCellPhone);

        // Фрейм Add Broker приховуємо warning блок
        SelenideElement warningBlock = $(".has-success .warning-block-wrapper");
        executeJavaScript("arguments[0].style.display='none';", warningBlock);

        // Фрейм Add Broker кнопка Submit, закриття фрейму Add broker
        $("#add_broker_send").shouldBe(clickable).click();
        $("#add_broker").shouldNotBe(visible, EXPECT_GLOBAL);

        // Перевіряє створеного брокера в полі Broker
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $(".bt-load-broker-main-flex").shouldHave(text(atLegalName));
        $(".bt-load-broker-main-flex").shouldHave(text(atDbaName));
        $(".bt-load-broker-main-flex").shouldHave(text(atCity));
        $(".bt-load-broker-main-flex").shouldHave(text(atAddress));
        $(".bt-load-broker-main-flex").shouldHave(text(atMainPhoneNumber));

        // Фрейм New load вибирає Агента в полі Select Agent
        $$("select#loads-agent_id option").findBy(text(atAgentName)).click();
        $("#loads-agent_id").shouldHave(text(atAgentName + " " + atAgentLastName));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
