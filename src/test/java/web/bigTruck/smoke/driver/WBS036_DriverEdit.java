package web.bigTruck.smoke.driver;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataBt.WCB001_Driver;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS036_DriverEdit {

    // Click Up:
    // CRM SEMI Truck
    // Driver
    // 1. Создание водителя

    // Global data
    String globalNumberSeventeen = "12" + GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();
    WCB001_Driver driverCreate = new WCB001_Driver();

    @Test
    public void edit() {

        //Create Driver
        WCB001_Driver newDriver = driverCreate.create();

        // Login
        GlobalLogin.login("bt_disp1");

        // Data for creating a Driver
        final String atFirstName = newDriver.atFirstName;
        final String atEditLastName = newDriver.globalName + "Driver Last Name2";
        final String atEditOwner = "AutoTestOwner5 INC";
        final String atEditAddress = "Mountain";
        final String atEditCity = "San Antonio";
        final String atEditState = "TX";
        final String atEditLicenseState = "KS";
        final String atEditZip = "66105";
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
        final String atEditImportantInformation = "Other company sticker";
        final String atEditNotes = "Notes2";
        final String atEditDescription = "Description2";

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Drivers] Table. Select Drivers "Without Unit"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
//        $("#driverssearch-is_driver_has_truck").selectOption("Without Unit");
//        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("Without Unit"));
        SelenideElement rowTableUpdateDriver = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);

        // [Main Drivers] Table. Choose Pencil
        rowTableUpdateDriver.shouldHave(text(atFirstName));
        rowTableUpdateDriver.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // [Update Drivers] Tab General. Update data
        $("#update_driver").shouldBe(visible);
        $("#drivers-last_name").setValue(atEditLastName);

        // [Update Driver] Tab General. Input data
        $("#drivers-first_name").shouldHave(value(atFirstName));
        $("#drivers-last_name").setValue(atEditLastName);

        // [Update Driver] Tab General. Input data
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
        Calendar.setDate(5);

        // [Update Driver] Tab General. Input Calendar License Expiration
        $("#drivers-license_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(5);

        // [Update Driver] Tab General. Input data
        $("#drivers-license_number").setValue(atEditLicenseNumber);
        $("#drivers-ssn").setValue(atEditSsn);
        $("#drivers-hired_by").selectOption(atEditHiredBy);
        $("#drivers-license_state").setValue(atEditLicenseState);
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

        // [Update Driver] Tab Owners. Select owner
        $$(".nav-tabs li").findBy(text("Owners")).click();
        SelenideElement newBlockOwner = $$(".carrier_block label").findBy(text(newDriver.atOwner)).closest(".carrier_block");
        newBlockOwner.$("label").click(); //прибирає овнера доданого при створенні

        SelenideElement editBlockOwner = $$(".carrier_block label").findBy(text(atEditOwner)).closest(".carrier_block");
        editBlockOwner.$("label").click(); // вибирає іншого овнера

        // [Update Driver] Tab Owners. Calendar Hire Date
        editBlockOwner.$$("label")
                .findBy(text("Hire Date"))
                .parent()
                .$(".kv-date-picker")
                .click();
        Calendar.setDate(5);

        // [Update Driver] Tab Owners. Calendar MVR Annual date
        editBlockOwner.$$("label")
                .findBy(text("MVR Annual date"))
                .parent()
                .$(".kv-date-picker")
                .click();
        Calendar.setDate(5);

        // [Update Driver] Tab Owners. Annual Query
        editBlockOwner.$$("label")
                .findBy(text("Annual Query"))
                .parent()
                .$(".kv-date-picker")
                .click();
        Calendar.setDate(5);

        // [Update Driver] Tab General. Button Submit
        $$(".nav-tabs li").findBy(text("General")).click();
        $("#update_driver_send").click();
        $("#update_driver").shouldNotBe(visible);

        // [Toast] Check message
        Message.checkToast("Driver sucessfully updated");

        // [Main Drivers] Table. Check new Drivers
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        SelenideElement rowTableDriver = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);
        rowTableDriver.shouldHave(text(atFirstName));
        rowTableDriver.shouldHave(text(atEditLastName));
        rowTableDriver.shouldNotHave(text(newDriver.atOwner)); // овнер доданий при створенні не відображається
        rowTableDriver.shouldHave(text(atEditOwner));
        rowTableDriver.shouldHave(text(atEditEmail));
        rowTableDriver.shouldHave(text(atEditCellPhone));
        rowTableDriver.shouldHave(text(atEditHiredBy));
        rowTableDriver.shouldHave(text(atEditSsn));
        rowTableDriver.shouldHave(text(atEditLicenseNumber));
        rowTableDriver.shouldHave(text(atEditLicenseState));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
