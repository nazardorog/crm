package utilsWeb.createDataExp;

import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class WCD003_ShipperReceiver {

    // Global data
    public static final String globalName = GlobalGenerateName.globalName();
    public static final String globalMail = GlobalGenerateName.globalMail();

    public String atName, atStreet1, atStreet2, atLocation, atLocationName, atEmail, atNote;

    @Test
    public void runTest() {
        create();
    }

    public WCD003_ShipperReceiver create() {

        // Data for creating a Shipper Receiver
        this.atName = globalName + "Shipper Receiver Name";
        this.atStreet1 = "Street 11";
        this.atStreet2 = "Street 21";
        this.atLocation = "66105";
        this.atLocationName = "Kansas City, KS 66105";
        this.atEmail = globalMail;
        this.atNote = "Note1";

        // Login
        GlobalLogin.login("exp_disp1");

        // [Sidebar] Go to Main Shipper Receiver
        $(".shippers-receivers-user").hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        // [Main Shipper Receiver] Button New Drivers
        $("#new_shippers-receiver").click();
        $("#add_shippers-receiver").shouldBe(visible);

        // [New Shipper Receiver] Input this
        $("#shippersreceivers-name").setValue(this.atName);
        $("#shippersreceivers-location").setValue(this.atLocation);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(text(this.atLocationName)).click();
        $("#shippersreceivers-location").shouldHave(value(this.atLocationName));

        // [New Shipper Receiver] Button Submit
        $("#add_shippers-receiver_send").click();
        $("#add_shippers-receiver").shouldNotBe(visible);

        // Delete toast message
        Message.dellToast();

        // Close web driver
        CloseWebDriver.tearDown();

        return this;
    }
}
