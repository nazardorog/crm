package utilsWeb.createDataExp;

import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WCD001_Driver {

    // Global data
    public static final String globalName = GlobalGenerateName.globalName();
    public static final String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    public static final String globalMail = GlobalGenerateName.globalMail();
    public static final String globalNumberNine = "12" + GlobalGenerateName.globalNumberSeven();

    public String atFirstName, atLastName, atOwner, atAddress, atCity, atState, atZip,
            atCellPhone, atEmail, atEmergencyContactPhone, atEmergencyContactName,
            atLicenseNumber, atSsn;

    @Test
    public void runTest() {
        create();
    }

    public WCD001_Driver create() {

        // Data for creating a Driver
        this.atFirstName = globalName + "Driver First Name";
        this.atLastName = globalName + "Last Name";
        this.atOwner = "Autotest 3 Owner";
        this.atAddress = "Mountain";
        this.atCity = "Kansas City";
        this.atState = "KS";
        this.atZip = "66105";
        this.atCellPhone = globalPhoneNumber;
        this.atEmail = globalMail;
        this.atEmergencyContactPhone = globalPhoneNumber;
        this.atEmergencyContactName = globalName + "Emergency Contact Name";
        this.atLicenseNumber = globalNumberNine;
        this.atSsn = globalNumberNine;

        // Login
        GlobalLogin.login("exp_hr");

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Drivers] Button New Trucks
        $("#new_driver").click();
        $("#add_driver").shouldBe(visible);

        // [Add Drivers] Tab General. Input data
        $("#drivers-first_name").setValue(this.atFirstName);
        $("#drivers-last_name").setValue(this.atLastName);

        // [Add Drivers] Tab General. Input Owner this
        $("#select2-owner_id-create-container").click();
        $(".select2-search--dropdown > input").setValue("auto");
        $$("li.select2-results__option").findBy(text(this.atOwner)).click();

        // [Add Drivers] Tab General. Input
        $("#drivers-address").setValue(this.atAddress);
        $("#drivers-zip").setValue(this.atZip);
        $("#drivers-state").setValue(this.atState);
        $("#drivers-city").setValue(this.atCity);
        $("#drivers-cell_phone").setValue(this.atCellPhone);
        $("#drivers-email").setValue(this.atEmail);
        $("#home_phone-create").setValue(this.atEmergencyContactPhone);
        $("#drivers-emergency_contact_name").setValue(this.atEmergencyContactName);

        // [Add Drivers] Tab General. Input Calendar License Expiration
        $("#drivers-license_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // [Add Drivers] Tab General. Input Calendar Driving Record
        $("#drivers-driving_record-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // [Add Drivers] Tab General. Input
        $("#drivers-license_number").setValue(this.atLicenseNumber);
        $("#drivers-ssn").setValue(this.atSsn);

        // [Add Drivers] Button Submit
        $("#add_driver_send").click();
        $("#add_driver").shouldNotBe(visible);

        // Delete toast message
        Message.dellToast();

        // Close web driver
        CloseWebDriver.tearDown();

        return this;
    }
}
