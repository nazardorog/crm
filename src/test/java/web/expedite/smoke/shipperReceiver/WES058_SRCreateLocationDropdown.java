package web.expedite.smoke.shipperReceiver;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES058_SRCreateLocationDropdown {

    // Click Up:
    // CRM EXPEDITE - Smoke - Shippers-receivers
    // 2. Create -> Location dropdown

    // Global data
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void createLocationDropdown() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for creating a Shipper Receiver
        final String atName = globalName + "Shipper Receiver Name";
        final String atStreet1 = "Street 11";
        final String atStreet2 = "Street 21";
        final String atCountry = "US";
        final String atLocation = "66105";
        final String atLocationName = "Kansas City, KS 66105";
        final String atEmail = globalMail;
        final String atContactPersonName = "Contact Person Name1";
        final String atContactPersonPhoneNumber = globalPhoneNumber;
        final String atContactCellNumber = globalPhoneNumber;
        final String atNote = "Note1";
        final String atLat = "39";
        final String atLng = "-94";

        // [Sidebar] Go to Main Shipper Receiver
        $(".shippers-receivers-user").hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        // [Main Shipper Receiver] Button New Drivers
        $("#new_shippers-receiver").click();
        $("#add_shippers-receiver").shouldBe(visible);

        // [New Shipper Receiver] Input data
        $("#shippersreceivers-name").setValue(atName);
        $("#shippersreceivers-street1").setValue(atStreet1);
        $("#shippersreceivers-street2").setValue(atStreet2);
        $("#country_shippers_receivers").selectOption(atCountry);
        $("#shippersreceivers-location").setValue(atLocation);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(text(atLocationName)).click();
        $("#shippersreceivers-location").shouldHave(value(atLocationName));
        $("#shippersreceivers-email").setValue(atEmail);
        $("#shippersreceivers-contact_person_name").setValue(atContactPersonName);
        $("#contact_person_phone_number-create").setValue(atContactPersonPhoneNumber);
        $("#shippersreceivers-contact_person_cell_number").setValue(atContactCellNumber);
        $("#shippersreceiversdetail-note").setValue(atNote);

        // [New Shipper Receiver] Button Submit
        $("#add_shippers-receiver_send").click();
        $("#add_shippers-receiver").shouldNotBe(visible);

        // Check toast message
        Message.checkToast("Shippers Receivers created successfully");

        // [Main Shipper Receiver] Table
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atName).pressEnter();
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0).shouldHave(text(atName), EXPECT_GLOBAL);
        rowTable.shouldHave(text(atName));
        rowTable.shouldHave(text(atLocationName));
        rowTable.shouldHave(text(atLocationName));
        rowTable.shouldHave(text(atStreet1));
        rowTable.shouldHave(text(atStreet2));
        rowTable.shouldHave(matchText(".*\\b" + atLat + "\\..*"));
        rowTable.shouldHave(matchText(".*\\" + atLng + "\\..*"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
