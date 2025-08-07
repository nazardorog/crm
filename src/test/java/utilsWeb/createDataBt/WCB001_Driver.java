package utilsWeb.createDataBt;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.configWeb.GlobalLogin;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WCB001_Driver {

    // Global data
    public static final String globalName = GlobalGenerateName.globalName();
    public static final String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    public static final String globalMail = GlobalGenerateName.globalMail();
    public static final String globalNumberNine = "12" + GlobalGenerateName.globalNumberSeven();

    public String atFirstName, atLastName, atOwner, atAddress, atCity, atState, atZip,
            atCellPhone, atEmail, atLicenseState, atEmergencyContactName,
            atLicenseNumber, atSsn;

    @Test
    public void runTest() {
        create();
    }

    public WCB001_Driver create() {

        // Data for creating a Driver
        this.atFirstName = globalName + "Driver First Name";
        this.atLastName = globalName + "Driver Last Name";
        this.atOwner = "AutoTestOwner4 INC";
        this.atAddress = "Mountain";
        this.atCity = "Kansas City";
        this.atState = "KS";
        this.atLicenseState = "KS";
        this.atCellPhone = globalPhoneNumber;
        this.atEmail = globalMail;
        this.atLicenseNumber = globalNumberNine;

        // Login
        GlobalLogin.login("bt_disp1");

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Driver] Button New Drivers
        $("#new_driver").click();
        $("#add_driver").shouldBe(visible);

        // [New Driver] Tab General. Input data
        $("#drivers-first_name").setValue(atFirstName);
        $("#drivers-last_name").setValue(atLastName);

        // [New Driver] Tab General. Input data
        $("#drivers-cell_phone").setValue(atCellPhone);
        $("#drivers-email").setValue(atEmail);

        // [New Driver] Tab General. Input Calendar License Expiration
        $("#drivers-license_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(5);

        // [New Driver] Tab General. Input data
        $("#drivers-license_number").setValue(atLicenseNumber);
        $("#drivers-license_state").setValue(atLicenseState);

        // [New Driver] Tab Owners. Select owner
        $$(".nav-tabs li").findBy(text("Owners")).click();
        SelenideElement blockOwner = $$(".carrier_block label").findBy(text(atOwner)).closest(".carrier_block");
        blockOwner.$("label").click();

        // [New Driver] Tab Owners. Calendar Hire Date
        blockOwner.$$("label")
                .findBy(text("Hire Date"))
                .parent()
                .$(".kv-date-picker")
                .click();
        Calendar.setDate(5);

        // [New Driver] Tab Owners. Calendar MVR Annual date
        blockOwner.$$("label")
                .findBy(text("MVR Annual date"))
                .parent()
                .$(".kv-date-picker")
                .click();
        Calendar.setDate(5);

        // [New Driver] Tab Owners. Annual Query
        blockOwner.$$("label")
                .findBy(text("Annual Query"))
                .parent()
                .$(".kv-date-picker")
                .click();
        Calendar.setDate(5);

        // [New Driver] Tab General. Button Submit
        $$(".nav-tabs li").findBy(text("General")).click();
        $("#add_driver_send").click();
        $("#add_driver").shouldNotBe(visible);

        // Delete toast message
        Message.dellToast();

        // Close web driver
        CloseWebDriver.tearDown();

        return this;
    }
}
