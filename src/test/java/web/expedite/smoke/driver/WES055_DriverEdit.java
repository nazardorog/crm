package web.expedite.smoke.driver;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataExp.WCE001_Driver;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES055_DriverEdit {

    // Click Up:
    // CRM EXPEDITE - Smoke - Drivers
    // 2. Редактирование driver

    // Global data
    String globalNumberSeventeen = "12" + GlobalGenerateName.globalNumberSeven();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();
    WCE001_Driver driverCreate = new WCE001_Driver();

    @Test
    public void edit() {

        //Create Driver
        WCE001_Driver newDriver = driverCreate.create();

        // Login
        GlobalLogin.login("exp_hr");

        // Data for edit a Driver
        final String atFirstName = newDriver.atFirstName;
        final String atEditLastName = newDriver.globalName + "Driver Last Name2";
        final String atEditOwner = "Autotest 4 Owner";
        final String atEditTruck = "0308";
        final String atEditAddress = "Mountain";
        final String atEditCity = "San Antonio";
        final String atEditState = "TX";
        final String atEditZip = "78229";
        final String atEditCellPhone = globalPhoneNumber;
        final String atEditEmail = globalMail;
        final String atEditAdditionalPhones = globalPhoneNumber;
        final String atEditAdditionalEmails = globalMail;
        final String atEditEmergencyContactPhone = globalPhoneNumber;
        final String atEditEmergencyContactName = "Emergency Contact Name12";
        final String atEditEmergencyContactPhone2 = globalPhoneNumber;
        final String atEditEmergencyContactName2 = "Emergency Contact Name22";
        final String atEditLicenseNumber = globalNumberSeventeen;
        final String atEditSsn = globalNumberSeventeen;
        final String atEditPhoto = "4jpeg.jpg";
        final String atEditFile = "1pdf.pdf";
        final String atEditHiredBy = "Agent003 HR";
        final String atEditNotifications = "Location";
        final String atEditImportantInformation = "Other company sticker";
        final String atEditNotes = "Notes2";
        final String atEditDescription = "Description2";

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Drivers] Table. Select Drivers "Without Unit"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        $("#driverssearch-is_driver_has_truck").selectOption("Without Unit");
        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("Without Unit"));
        SelenideElement rowTableUpdateDriver = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);

        // [Main Drivers] Table. Choose Pencil
        rowTableUpdateDriver.shouldHave(text(atFirstName));
        rowTableUpdateDriver.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // [Update Drivers] Tab General. Update data
        $("#update_driver").shouldBe(visible);
        $("#drivers-last_name").setValue(atEditLastName);

        // [Update Drivers] Tab General. Update Owner data
        $("#select2-owner_id-update-container").click();
        $(".select2-search--dropdown > input").setValue("auto");
        $$("li.select2-results__option").findBy(text(atEditOwner)).click();

        // [Update Drivers] Tab General. Update Truck data
        $("#select2-trucks_id-update-container").click();
        $(".select2-search--dropdown > input").setValue(atEditTruck);
        $$("li.select2-results__option").findBy(text(atEditTruck)).click();

        // [Update Driver] Tab General. Update data
        $("#drivers-address").setValue(atEditAddress);
        $("#drivers-zip").setValue(atEditZip);
        $("#drivers-state").setValue(atEditState);
        $("#drivers-city").setValue(atEditCity);
        $("#drivers-cell_phone").setValue(atEditCellPhone);
        $("#drivers-email").setValue(atEditEmail);
        $("button.add-additional-phone").click();
        $("#driveradditionalinfo-0-phone-value").setValue(atEditAdditionalPhones);
        $("button.add-additional-email").click();
        $("#driveradditionalinfo-0-email-value").setValue(atEditAdditionalEmails);
        $("#home_phone-update").setValue(atEditEmergencyContactPhone);
        $("#drivers-emergency_contact_name").setValue(atEditEmergencyContactName);
        $("#home_phone2-update").setValue(atEditEmergencyContactPhone2);
        $("#drivers-emergency_contact_name2").setValue(atEditEmergencyContactName2);

        // [Update Driver] Tab General. Input Calendar Date of Birth
        $("#drivers-date_birth-kvdate").click();
        Calendar.setDate(2);

        // [Update Driver] Tab General. Input Calendar Hired On
        $("#drivers-hire_date-kvdate .kv-date-picker").click();
        Calendar.setDate(2);

        // [Update Driver] Tab General. Input Calendar License Expiration
        $("#drivers-license_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(2);

        // [Update Driver] Tab General. Input Calendar Driving Record
        $("#drivers-driving_record-kvdate .kv-date-picker").click();
        Calendar.setDate(2);

        // [Update Driver] Tab General. Input data
        $("#drivers-license_number").setValue(atEditLicenseNumber);
        $("#drivers-ssn").setValue(atEditSsn);
        $("#drivers-hired_by").selectOption(atEditHiredBy);
        $("#general > div:nth-child(17) > div > div").click();
        $$("li.select2-results__option").findBy(text(atEditNotifications)).click();
        $("#drivers-important_information").selectOption(atEditImportantInformation);
        $("#driversdetail-notes").setValue(atEditNotes);

        // [Update Driver] Tab General. Download photo
        File photo = new File(downloadsFolder + atEditPhoto);
        $("#photo_file-update").uploadFile(photo);

        // [Update Driver] Tab Documents. Download file
        File file = new File(downloadsFolder + atEditFile);
        $$(".nav-tabs li").findBy(text("Documents")).click();
        $("button.add-document").click();
        $("#driverdocuments-0-file").uploadFile(file);
        $(".file-preview-other").shouldBe(visible, EXPECT_GLOBAL);
        $("#driverdocuments-0-description").setValue(atEditDescription);

        // [Update Driver] Button Submit
        $("#update_driver_send").click();
        $("#update_driver").shouldNotBe(visible);

        // Check toast message
        Message.checkToast("Driver sucessfully updated");

        // [Main Drivers] Table. Select Drivers "With Unit"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        $("#driverssearch-is_driver_has_truck").selectOption("With Unit");
        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("With Unit"));
        SelenideElement rowTableDriverAfterAddTruck = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);

        // [Main Drivers] Table. Check driver "With Unit"
        rowTableDriverAfterAddTruck.shouldHave(text(atFirstName));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditLastName));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditEmail));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditCellPhone));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditState));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditTruck));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditHiredBy));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditLicenseNumber));
        rowTableDriverAfterAddTruck.shouldHave(text(atEditSsn));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
