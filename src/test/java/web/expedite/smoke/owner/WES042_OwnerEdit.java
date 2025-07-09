package web.expedite.smoke.owner;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES042_OwnerEdit {

    // Click Up:
    // CRM EXPEDITE - Smoke - Owners
    // 4. Редактирование owner

    // Global data
    String globalNumberSeven = GlobalGenerateName.globalNumberSeven();
    String globalNumberNine = GlobalGenerateName.globalNumberNine();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void edit() {

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
        final String atFile = "4jpeg.jpg";
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
        $("input[name='OwnersSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        $("#ownerssearch-owners_asset").selectOption("All");

        // [Main Owners] Table. Check new Owners
        SelenideElement rowTableAfter = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);
        rowTableAfter.shouldHave(text(atType));
        rowTableAfter.shouldHave(text(atFirstName));
        rowTableAfter.shouldHave(text(atLastName));
        rowTableAfter.shouldHave(text(atHrAgent));

        // [Main Owners] Table. Edit Owners
        rowTableAfter.$("button.dropdown-toggle").shouldBe(visible, EXPECT_GLOBAL).shouldBe(clickable, EXPECT_GLOBAL).hover().click();
        rowTableAfter.$(".btn-group").shouldHave(cssClass("open"),EXPECT_GLOBAL);
        ElementsCollection dropDownBroker = rowTableAfter.$$(".dropdown-menu-right li");
        dropDownBroker.findBy(exactText("Update")).click();

        // Data for edit a Broker
        globalNumberSeven = GlobalGenerateName.globalNumberSeven();
        globalNumberNine = GlobalGenerateName.globalNumberNine();
        globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
        globalMail = GlobalGenerateName.globalMail();

        final String atEditLastName = globalName + "Last Name2";
        final String atEditStreet1 = globalName + "Street 12";
        final String atEditStreet2 = globalName + "Street 22";
        final String atEditCity = "San Antonio";
        final String atEditState = "TX";
        final String atEditZip = "78229";
        final String atEditPhone = globalPhoneNumber;
        final String atEditAdditionalPhones = globalPhoneNumber;
        final String atEditEmail = globalMail;
        final String atEditAdditionalEmails = globalMail;
        final String atEditFile = "jpeg2.jpg";
        final String atEditHrAgent = "Damon Pierce";
        final String atEditCargoPolicy = globalNumberSeven;
        final String atEditLiabilityPolicy = globalNumberSeven;
        final String atEditCargoCoverage = "20000000";
        final String atEditLiabilityCoverage = "200000000";
        final String atEditSsn = globalNumberNine;
        final String atEditBankName = globalName + "Bank Name2";
        final String atEditRoutingNumber = globalNumberNine;
        final String atEditAccountNumber = globalName + "Account Number2";
        final String atEditAccountHolder = "Account Holder2";
        final String atEditAccountType = "Savings";
        final String atEditDescription = "Description2";

        // [Update Owner] Tab General. Edit data
        $("#update_owner").shouldBe(visible, EXPECT_GLOBAL);
        $$("#owners-type label").findBy(text(atType)).$("input[type='radio']").shouldBe(checked);
        $("#owners-last_name").setValue(atEditLastName);
        $("#owners-street1").setValue(atEditStreet1);
        $("#owners-street2").setValue(atEditStreet2);
        $("#owners-zip").setValue(atEditZip);
        $("#owners-state").setValue(atEditState);
        $("#owners-city").setValue(atEditCity);
        $("#owners-phone_number").setValue(atEditPhone);
        $("#owneradditionalinfo-0-phone-value").setValue(atEditAdditionalPhones);
        $("#owners-email").setValue(atEditEmail);
        $("#owneradditionalinfo-0-email-value").setValue(atEditAdditionalEmails);
        $("#owners-hr_agent").selectOption(atEditHrAgent);
        $("#owners-cargo_policy").setValue(atEditCargoPolicy);
        $("#owners-liability_policy").setValue(atEditLiabilityPolicy);
        $("#owners-cargo_insurance_amount-disp").setValue(atEditCargoCoverage);
        $("#owners-liability_insurance_amount-disp").setValue(atEditLiabilityCoverage);
        $("#owners-ssn").setValue(atEditSsn);

        // [Update Owner] Tab General. Calendar Birthday
        $("#owners-birthday-kvdate .kv-date-picker").click();
        Calendar.setDate(2);

        // [Update Owner] Tab General. Calendar Insurance Expiration
        $("#owners-insurance_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(20);

        // [Update Owner] Tab Payments. Input data
        $$(".nav-tabs li").findBy(text("Payments")).click();
        $("#ownerpayments-0-bank_name").setValue(atEditBankName);
        $("#ownerpayments-0-routing_number").setValue((atEditRoutingNumber));
        $("#ownerpayments-0-account_number").setValue((atEditAccountNumber));
        $("#ownerpayments-0-account_holder").setValue(atEditAccountHolder);
        $("#ownerpayments-0-account_type").selectOption(atEditAccountType);

        // [Update Owner] Tab Documents. Download file.
        $$(".nav-tabs li").findBy(text("Documents")).click();
        $("button.remove-document").click();
        $("button.add-document").click();
        $(".field-ownerdocuments-0-file").shouldBe(visible, EXPECT_GLOBAL);
        File editFile = new File(downloadsFolder + atEditFile);
        $("#ownerdocuments-0-file").uploadFile(editFile);
        $("#ownerdocuments-0-description").setValue(atEditDescription);

        // [Update Owner] Tab Works in our companies. Check checkbox
        $$(".nav-tabs li a").findBy(text("Works in our companies")).click();
        $$("#companies").findBy(text("Empire National Inc")).$("input[type='checkbox']").shouldBe(checked);

        // [Update Owner] Button Submit
        $("#update_owner_send").shouldBe(clickable).click();
        $("#update_owner").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Owner sucessfully updated"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // [Main Owners] Table. Check edit Owners
        rowTableAfter.shouldHave(text(atType));
        rowTableAfter.shouldHave(text(atFirstName));
        rowTableAfter.shouldHave(text(atEditLastName));
        rowTableAfter.shouldHave(text(atEditHrAgent));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
