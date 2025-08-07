package web.bigTruck.smoke.driver;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS035_DriverCreate {

    // Click Up:
    // CRM SEMI Truck
    // Driver
    // 1. Создание водителя

    // Global data
    String globalNumberSeventeen = "12" + GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void create() {

        // Login
        GlobalLogin.login("bt_disp1");

        // Data for creating a Driver
        final String atFirstName = globalName + "Driver First Name";
        final String atLastName = globalName + "Driver Last Name";
        final String atOwner = "AutoTestOwner4 INC";
        final String atAddress = "Mountain";
        final String atCity = "Kansas City";
        final String atState = "KS";
        final String atLicenseState = "KS";
        final String atZip = "66105";
        final String atCellPhone = globalPhoneNumber;
        final String atEmail = globalMail;
        final String atAdditionalPhones = globalPhoneNumber;
        final String atAdditionalEmails = globalMail;
        final String atEmergencyContactPhone = globalPhoneNumber;
        final String atEmergencyContactName = "Emergency Contact Name";
        final String atEmergencyContactPhone2 = globalPhoneNumber;
        final String atEmergencyContactName2 = "Emergency Contact Name 2";
        final String atLicenseNumber = globalNumberSeventeen;
        final String atSsn = globalNumberSeventeen;
        final String atPhoto = "4jpeg.jpg";
        final String atFile = "1pdf.pdf";
        final String atHiredBy = "Agent003 HR";
        final String atImportantInformation = "Other company sticker";
        final String atNotes = "Notes";
        final String atDescription = "Description";

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
        $("#drivers-address").setValue(atAddress);
        $("#drivers-zip").setValue(atZip);
        $("#drivers-state").setValue(atState);
        $("#drivers-city").setValue(atCity);
        $("#drivers-cell_phone").setValue(atCellPhone);
        $("#drivers-email").setValue(atEmail);
        $("button.add-additional-phone").click();
        $("#driveradditionalinfo-0-phone-value").setValue(atAdditionalPhones);
        $("button.add-additional-email").click();
        $("#driveradditionalinfo-0-email-value").setValue(atAdditionalEmails);
        $("#home_phone-create").setValue(atEmergencyContactPhone);
        $("#drivers-emergency_contact_name").setValue(atEmergencyContactName);
        $("#home_phone2-create").setValue(atEmergencyContactPhone2);
        $("#drivers-emergency_contact_name2").setValue(atEmergencyContactName2);

        // [New Driver] Tab General. Input Calendar Date of Birth
        $("#drivers-date_birth-kvdate").click();
        Calendar.setDate(5);

        // [New Driver] Tab General. Input Calendar License Expiration
        $("#drivers-license_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(5);

        // [New Driver] Tab General. Input data
        $("#drivers-license_number").setValue(atLicenseNumber);
        $("#drivers-ssn").setValue(atSsn);
        $("#drivers-hired_by").selectOption(atHiredBy);
        $("#drivers-license_state").setValue(atLicenseState);
        $("#drivers-important_information").selectOption(atImportantInformation);
        $("#driversdetail-notes").setValue(atNotes);

        // [New Driver] Tab General. Download photo
        File photo = new File(downloadsFolder + atPhoto);
        $("#photo_file-create").uploadFile(photo);

        // [New Driver] Tab Documents. Download file
        File file = new File(downloadsFolder + atFile);
        $$(".nav-tabs li").findBy(text("Documents")).click();
        $("button.add-document").click();
        $("#driverdocuments-0-file").uploadFile(file);
        $(".file-preview-other").shouldBe(visible, EXPECT_GLOBAL);
        $("#driverdocuments-0-description").setValue(atDescription);

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

        // [Toast] Check message
        Message.checkToast("Driver sucessfully added");

        // [Main Drivers] Table. Check new Drivers
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        SelenideElement rowTableDriver = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);
        rowTableDriver.shouldHave(text(atFirstName));
        rowTableDriver.shouldHave(text(atLastName));
        rowTableDriver.shouldHave(text(atOwner));
        rowTableDriver.shouldHave(text(atOwner));
        rowTableDriver.shouldHave(text(atEmail));
        rowTableDriver.shouldHave(text(atCellPhone));
        rowTableDriver.shouldHave(text(atHiredBy));
        rowTableDriver.shouldHave(text(atSsn));
        rowTableDriver.shouldHave(text(atLicenseNumber));
        rowTableDriver.shouldHave(text(atLicenseState));

        // [View Driver] Check data New driver
        rowTableDriver.$(".glyphicon-eye-open").click();
        $("#view_driver").shouldBe(visible, EXPECT_GLOBAL);
        $$("table#w0 tr").findBy(text("First Name"))                    .$$("td").first().shouldHave(text(atFirstName));
        $$("table#w0 tr").findBy(text("Last Name"))                     .$$("td").first().shouldHave(text(atLastName));
        $$("table#w0 tr").findBy(text("Owner"))                         .$$("td").first().shouldHave(text(atOwner));
        $$("table#w0 tr").findBy(text("SSN"))                           .$$("td").first().shouldHave(text("*****"));
        $$("table#w0 tr").findBy(text("Address"))                       .$$("td").first().shouldHave(text(atAddress));
        $$("table#w0 tr").findBy(text("City"))                          .$$("td").first().shouldHave(text(atCity));
        $$("table#w0 tr").findBy(text("State"))                         .$$("td").first().shouldHave(text(atState));
        $$("table#w0 tr").findBy(text("Zip"))                           .$$("td").first().shouldHave(text(atZip));
        $$("table#w0 tr").findBy(text("Cell Phone"))                    .$$("td").first().shouldHave(text(atCellPhone));
        $$("table#w0 tr").findBy(text("Emergency Contact phone"))       .$$("td").first().shouldHave(text(atEmergencyContactPhone));
        $$("table#w0 tr").findBy(text("Emergency Contact Name"))        .$$("td").first().shouldHave(text(atEmergencyContactName));
        $$("table#w0 tr").findBy(text("Emergency Contact phone 2"))     .$$("td").first().shouldHave(text(atEmergencyContactPhone2));
        $$("table#w0 tr").findBy(text("Emergency Contact Name 2"))      .$$("td").first().shouldHave(text(atEmergencyContactName2));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
