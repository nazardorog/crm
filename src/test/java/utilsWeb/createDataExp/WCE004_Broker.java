package utilsWeb.createDataExp;

import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WCE004_Broker {

    // Global data
    public static final String globalName = GlobalGenerateName.globalName();
    public static final String globalNumberSeven = GlobalGenerateName.globalNumberSeven();
    public static final String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    public static final String globalMail = GlobalGenerateName.globalMail();

    public String atMcNumber, atDbaName, atLegalName, atEntityType, atAddress, atCity, atNote,
            atMainName, atMail, atMainPhoneNumber, atFfName, atAgentName, atAgentLastName,
            atAgentMail, atAgentPhoneNumber, atAgentCellPhone;

    @Test
    public void runTest() {
        create();
    }

    public WCE004_Broker create() {

        // Data for creating a Broker
        this.atMcNumber = globalNumberSeven;
        this.atDbaName = globalName + "DBA Name";
        this.atLegalName = globalName + "Legal Name";
        this.atEntityType = "BROKER";
        this.atAddress = "Mountain";
        this.atCity = "Colorado";
        this.atNote = globalName + "Note";
        this.atMainName = globalName + "Main Name";
        this.atMail = globalMail;
        this.atMainPhoneNumber = globalPhoneNumber;
        this.atFfName = globalName + "2nd MC/FF Name";
        this.atAgentName = globalName + "Agent Name";
        this.atAgentLastName = globalName + "Agent Last Name";
        this.atAgentMail = "AgentMail_" + globalMail;
        this.atAgentPhoneNumber = globalPhoneNumber + "01";
        this.atAgentCellPhone = globalPhoneNumber + "02";

        // Login
        GlobalLogin.login("exp_disp1");

        // Create new load
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();
        $("#add_load").shouldBe(enabled, EXPECT_GLOBAL);

        // [Sidebar] Go to Load board and button New Broker
        $("#new_broker").click();
        $("#add_broker").shouldBe(visible, EXPECT_GLOBAL);

        // [Add Broker] Tab General. Input data
        $("#brokers-mc_number").shouldBe(visible, EXPECT_GLOBAL).hover().setValue(this.atMcNumber);
        $("#brokers-name").setValue(this.atLegalName);
        $("#brokers-address").setValue(this.atAddress);
        $("#brokers-city").setValue(this.atCity);

        // [Add Broker] Tab Contact. Input data
        $(byText("Contact")).click();
        $("#brokers-main_phone_number").setValue(this.atMainPhoneNumber);

        // [Add Broker] Tab Agents. Input data
        $(byText("Agents")).click();
        $("#agents-0-name").setValue(this.atAgentName);
        $("#agents-0-email").setValue(this.atAgentMail);
        $("#agents-0-phone_number").setValue(this.atAgentPhoneNumber);

        // [Add Broker] Button Submit
        $("#add_broker_send").shouldBe(clickable).click();
        $("#add_broker").shouldNotBe(visible, EXPECT_GLOBAL);

        // Close web driver
        CloseWebDriver.tearDown();

        return this;
    }
}
