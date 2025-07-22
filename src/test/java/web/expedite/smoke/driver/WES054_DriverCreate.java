package web.expedite.smoke.driver;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES054_DriverCreate {

    // Click Up:
    // CRM EXPEDITE - Smoke - Drivers
    // 1. Создание New driver

    // Global data
    String globalNumberSeventeen = "12" + GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test(description = "тест в description")
    @Story("Owner")
    @Description("дескріпш")
    public void create() {

        // Встановлюємо кастомну назву для тесту
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("1. Создание New driver");
        });

        // Login
        GlobalLogin.login("exp_hr");

        // Data for creating a Driver
        final String atFirstName = globalName + "Driver First Name";
        final String atLastName = globalName + "Driver Last Name";
        final String atOwner = "Autotest 3 Owner";
        final String atTruck = "0307";
        final String atAddress = "Mountain";
        final String atCity = "Kansas City";
        final String atState = "KS";
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
        final String atPhoto = "jpeg1.jpg";
        final String atFile = "1pdf.pdf";
        final String atHiredBy = "Agent003 HR";
        final String atNotifications = "uxCam";
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

        // [New Driver] Tab General. Input Owner data
        $("#select2-owner_id-create-container").click();
        $(".select2-search--dropdown > input").setValue("auto");
        $$("li.select2-results__option").findBy(text(atOwner)).click();

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
        Calendar.setDate(0);

        // [New Driver] Tab General. Input Calendar Hired On
        $("#drivers-hire_date-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // [New Driver] Tab General. Input Calendar License Expiration
        $("#drivers-license_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // [New Driver] Tab General. Input Calendar Driving Record
        $("#drivers-driving_record-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // [New Driver] Tab General. Input data
        $("#drivers-license_number").setValue(atLicenseNumber);
        $("#drivers-ssn").setValue(atSsn);
        $("#drivers-hired_by").selectOption(atHiredBy);
        $("#general > div:nth-child(17) > div > div").click();
        $$("li.select2-results__option").findBy(text(atNotifications)).click();
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

        // [New Driver] Button Submit
        $("#add_driver_send").click();
        $("#add_driver").shouldNotBe(visible);

        // Check toast message
        Message.checkToast("Driver sucessfully added");

        // [Main Drivers] Table. Select Drivers "Without Unit"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        $("#driverssearch-is_driver_has_truck").selectOption("Without Unit");
        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("Without Unit"));
        SelenideElement rowTableDriver = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);
        rowTableDriver.shouldHave(text(atFirstName));

        // [Main Drivers] Table. Right menu choose Update
        rowTableDriver.$("button.dropdown-toggle").shouldBe(clickable).click();
        rowTableDriver.$(".btn-group").shouldHave(cssClass("open"), EXPECT_GLOBAL);
        rowTableDriver.$$(".dropdown-menu-right li").findBy(text("Update")).shouldBe(enabled).click();

        // [Update Drivers] Tab General. Input Truck data
        $("#update_driver").shouldBe(visible);
        $("#select2-trucks_id-update-container").click();
        $(".select2-search--dropdown > input").setValue(atTruck);
        $$("li.select2-results__option").findBy(text(atTruck)).click();
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
        rowTableDriverAfterAddTruck.shouldHave(text(atLastName));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
