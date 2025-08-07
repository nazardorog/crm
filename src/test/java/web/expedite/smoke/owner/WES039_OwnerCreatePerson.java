package web.expedite.smoke.owner;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES039_OwnerCreatePerson {

    // Click Up:
    // CRM EXPEDITE - Smoke - Owners
    // 1. Создание owner (type person)

    // Global data
    String globalNumberSeven = GlobalGenerateName.globalNumberSeven();
    String globalNumberNine = GlobalGenerateName.globalNumberNine();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void createPerson() {

        // Login
        GlobalLogin.login("exp_hr");

        // Data for creating a Owner
        final String atType = "Person";
        final String atFirstName = globalName + "First Name";
        final String atLastName = globalName + "Last Name";
        final String atStreet1 = globalName + "Street 11";
        final String atStreet2 = globalName + "Street 21";
        final String atCity = "Philadelphia";
        final String atState = "PA";
        final String atZip = "19143";
        final String atPhone = globalPhoneNumber;
        final String atAdditionalPhones = globalPhoneNumber;
        final String atEmail = globalMail;
        final String atAdditionalEmails = globalMail;
        final String atFile = "1pdf.pdf";
        final String atHrAgent = "Jack Sparrow";
        final String atCargoPolicy = globalNumberSeven;
        final String atLiabilityPolicy = globalNumberSeven;
        final String atCargoCoverage = "10000000";
        final String atLiabilityCoverage = "100000000";
        final String atSsn = globalNumberNine;
        final String atBankName = globalName + "Bank Name";
        final String atRoutingNumber = globalNumberNine;
        final String atAccountNumber = globalName + "Account Number";
        final String atAccountHolder = "Account Holder";
        final String atAccountType = "Checking";
        final String atDescription = "Description";

        // [Sidebar] Go to Main Owners
        $(".owners-user").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".owners-user").click();
        $("body").click();

        // [Main Owners] Button New Owner
        $("#new_owner").shouldBe(visible).click();
        $("#add_owner").shouldBe(visible, EXPECT_GLOBAL);

        // [Add Owners] Tab General. Input data
        $$("#owners-type label").findBy(text(atType)).click();
        $$("#owners-type label").findBy(text(atType)).$("input[type='radio']").shouldBe(checked);
        $("#owners-first_name").setValue(atFirstName);
        $("#owners-last_name").setValue(atLastName);
        $("#owners-street1").setValue(atStreet1);
        $("#owners-street2").setValue(atStreet2);
        $("#owners-zip").setValue(atZip);
        $("#owners-state").setValue(atState);
        $("#owners-city").setValue(atCity);
        $("#owners-phone_number").setValue(atPhone);
        $("button.add-additional-phone").click();
        $("#owneradditionalinfo-0-phone-value").setValue(atAdditionalPhones);
        $("#owners-email").setValue(atEmail);
        $("button.add-additional-email").click();
        $("#owneradditionalinfo-0-email-value").setValue(atAdditionalEmails);
        $("#owners-hr_agent").selectOption(atHrAgent);
        $("#owners-cargo_policy").setValue(atCargoPolicy);
        $("#owners-liability_policy").setValue(atLiabilityPolicy);
        $("#owners-cargo_insurance_amount-disp").setValue(atCargoCoverage);
        $("#owners-liability_insurance_amount-disp").setValue(atLiabilityCoverage);
        $("#owners-ssn").setValue(atSsn);

        // [Add Owners] Tab General. Calendar Birthday
        $("#owners-birthday-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // [Add Owners] Tab General. Calendar Insurance Expiration
        $("#owners-insurance_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(10);

        // [Add Owners] Tab Payments. Input data
        $$(".nav-tabs li").findBy(text("Payments")).click();
        $("#ownerpayments-0-bank_name").setValue(atBankName);
        $("#ownerpayments-0-routing_number").setValue(String.valueOf(atRoutingNumber));
        $("#ownerpayments-0-account_number").setValue(String.valueOf(atAccountNumber));
        $("#ownerpayments-0-account_holder").setValue(atAccountHolder);
        $("#ownerpayments-0-account_type").selectOption(atAccountType);

        // [Add Owners] Tab Documents. Download file.
        $$(".nav-tabs li").findBy(text("Documents")).click();
        $("button.add-document").click();
        $(".field-ownerdocuments-0-file").shouldBe(visible, EXPECT_GLOBAL);
        File file = new File(downloadsFolder + atFile);
        $("#ownerdocuments-0-file").uploadFile(file);
        $("#ownerdocuments-0-description").setValue(atDescription);

        // [Add Owners] Tab Works in our companies. Check checkbox
        $$(".nav-tabs li a").findBy(text("Works in our companies")).click();
        $$("#companies").findBy(text("Empire National Inc")).$("input[type='checkbox']").shouldBe(checked);

        // [Add Owners] Button Submit
        $("#add_owner_send").shouldBe(clickable).click();

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Owner sucessfully added. And driver sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // [Main Owners] Table. Input Name. Select Owners Unit-"Without Unit". Select Type-"Person"
        $("input[name='OwnersSearch[name]']").shouldBe(visible).setValue(atFirstName);
        $("#ownerssearch-owners_asset").selectOption("All");
        $("#ownerssearch-owners_asset").getSelectedOption().shouldHave(text("All"));
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);
        $("#ownerssearch-owners_asset").selectOption("Without Unit");
        $("#ownerssearch-owners_asset").getSelectedOption().shouldHave(text("Without Unit"));
        $("#ownerssearch-type").selectOption("Person");

        // [Main Owners] Table. Check new Owners
        rowTable.shouldHave(text(atType));
        rowTable.shouldHave(text(atFirstName));
        rowTable.shouldHave(text(atLastName));
        rowTable.shouldHave(text(atHrAgent));

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Drivers] Table. Input Name. Select Drivers Unit-"Without Unit"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName);
        $("#driverssearch-is_driver_has_truck").selectOption("Without Unit");
        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("Without Unit"));
        SelenideElement rowTableDriver = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);

        // [Main Drivers] Table. Check driver created
        rowTableDriver.shouldHave(text(atFirstName));
        rowTableDriver.shouldHave(text(atLastName));
        rowTableDriver.shouldHave(text(atHrAgent));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
