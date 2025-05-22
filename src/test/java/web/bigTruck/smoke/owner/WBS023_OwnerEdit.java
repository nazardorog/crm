package web.bigTruck.smoke.owner;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class WBS023_OwnerEdit {

    // Click Up:
    // CRM SEMI Truck
    // Owners
    // 2. Редактирование owner

    String globalName = GlobalGenerateName.globalName();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void edit() {

        // Login
        GlobalLogin.login("bt_disp1");

        //переходить до списку Owners
        $(".owners-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".owners-user").click();
        $("body").click();

        //клік по кнопці New Owner
        $("#new_owner").shouldBe(visible).click();

        // *** Вкладка General фрейму Add owner ***

        //дані вкладка General
        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        String atCompanyName = globalName + "Company Name INC";
        String atOwnerName = globalName + "Owner Name" + randomNumber;
        String atStreet1 = globalName + "Street auto test 11";
        String atStreet2 = globalName + "Street auto test 12";
        String atCity = globalName + "City auto test 1";
        String atState = "TX";
        int atZip = 75216;
        String atPhone = "090888-8882";
        String atEmail = globalMail;
        int atCargoPolicy = 888899991;
        String atLiabilityPolicy = "" + 888899 + randomNumber;
        String atTaxId = "" + 888899 + randomNumber;

        //вводить дані вкладка General

        $("#owners-type label").shouldBe(visible).shouldHave(text("Company"))
                .$("input[type='radio']").shouldBe(checked);
        $("#owners-company_name").setValue(atCompanyName);
        $("#owners-owner_name").setValue(atOwnerName);
        $("#owners-street1").setValue(atStreet1);
        $("#owners-street2").setValue(atStreet2);
        $("#owners-zip").setValue(String.valueOf(atZip));
        $("#owners-state").setValue(atState);
        $("#owners-city").setValue(atCity);
        $("#owners-phone_number").setValue(atPhone);
        $("#owners-email").setValue(atEmail);
        $("#owners-cargo_policy").setValue(String.valueOf(atCargoPolicy));
        $("#owners-liability_policy").setValue(atLiabilityPolicy);
        $("#owners-tax_id_number").setValue(atTaxId);

        // calendar Insurance Expiration
        $("#owners-insurance_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);
        $("#owners-tax_id_number").click();

        // *** Вкладка Payments фрейму Add owner ***

        //дані вкладка Payments
        String atBankName = "Fulton Bank1";
        int atRoutingNumber = 888899994;
        int atAccountNumber = 888899995;
        String atAccountHolder = "AccountHolder1";

        //клік по вкладці Payments
        SelenideElement paymentsTab = $$(".nav-tabs li a").findBy(text("Payments"));
        actions().moveToElement(paymentsTab).perform();
        paymentsTab.shouldBe(visible, enabled).click();

        //вводить дані вкладка Payments
        $("#ownerpayments-0-bank_name").setValue(atBankName);
        $("#ownerpayments-0-routing_number").setValue(String.valueOf(atRoutingNumber));
        $("#ownerpayments-0-account_number").setValue(String.valueOf(atAccountNumber));
        $("#ownerpayments-0-account_holder").setValue(atAccountHolder);
        $("#ownerpayments-0-account_type").selectOption("Checking");

        // *** Вкладка Documents фрейму Add owner ***

        $$(".nav-tabs li a").findBy(text("Documents")).click();

        // *** Вкладка Works in our companies фрейму Add owner ***

        $$(".nav-tabs li a").findBy(text("Works in our companies")).click();

        //клік по кнопці Submit фрейм Add owner
        $("#add_owner_send").click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("Owner sucessfully added"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє створеного Owner в списку Owners
        $("input[name='OwnersSearch[name]']").shouldBe(visible, Duration.ofSeconds(10)).setValue(atOwnerName).pressEnter();
        $("#ownerssearch-owners_asset").selectOption("All");

        SelenideElement rowOwner = $$(".owners-td td").get(2);
        rowOwner.shouldHave(text(atCompanyName));
        rowOwner.shouldBe(text(atOwnerName), Duration.ofSeconds(20));

        //клік по кнопці три крапки вибір Update
        $(".owners-td button.dropdown-toggle").click();
        $(".dropdown-menu-right .update_owner")
                .shouldBe(visible)
                .shouldBe(enabled)
                .shouldBe(clickable)
                .click();
        $("#update_owner").shouldBe(visible);

        // *** Редагування Owner вкладка General фрейму Add owner ***

        //дані редагування вкладка General
        String atCompanyNameEdit = "Company name auto test 2" + randomNumber + " INC";
        String atOwnerNameEdit = "Owner name auto test 2" + randomNumber;
        String atStreet1Edit = "Street auto test 21";
        String atStreet2Edit = "Street auto test 22";
        String atCityEdit = "City auto test 2";
        String atStateEdit = "TX";
        int atZipEdit = 78229;
        String atPhoneEdit = "(099) 888-8882";
        String atEmailEdit = "EmailOwner2" + randomNumber + "@mail.com";
        int atCargoPolicyEdit = 988899991;
        String atLiabilityPolicyEdit = "" + 988899 + randomNumber;
        String atTaxIdEdit = "" + 988899 + randomNumber;

        //редагує дані вкладка General
        $("#owners-type label").shouldBe(visible).shouldHave(text("Company"))
                .$("input[type='radio']").shouldBe(checked);
        $("#owners-company_name").setValue(atCompanyNameEdit);
        $("#owners-street1").setValue(atStreet1Edit);
        $("#owners-street2").setValue(atStreet2Edit);
        $("#owners-zip").setValue(String.valueOf(atZipEdit));
        $("#owners-state").setValue(atStateEdit);
        $("#owners-city").setValue(atCityEdit);
        $("#owners-phone_number").setValue(atPhoneEdit);
        $("#owners-email").setValue(atEmailEdit);
        $("#owners-cargo_policy").setValue(String.valueOf(atCargoPolicyEdit));
        $("#owners-liability_policy").setValue(atLiabilityPolicyEdit);
        $("#owners-tax_id_number").setValue(atTaxIdEdit);

        // calendar Insurance Expiration
        $("#owners-insurance_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(1);
        $("#owners-tax_id_number").click();

        //дані редагування вкладка Payments
        String atBankNameEdit = "Fulton Bank2";
        int atRoutingNumberEdit = 988899994;
        int atAccountNumberEdit = 988899995;
        String atAccountHolderEdit = "AccountHolder2";

        //клік по вкладці Payments
        actions().moveToElement(paymentsTab).perform();
        paymentsTab.shouldBe(visible, enabled).click();

        //редагує дані вкладка Payments
        $("#ownerpayments-0-bank_name").setValue(atBankNameEdit);
        $("#ownerpayments-0-routing_number").setValue(String.valueOf(atRoutingNumberEdit));
        $("#ownerpayments-0-account_number").setValue(String.valueOf(atAccountNumberEdit));
        $("#ownerpayments-0-account_holder").setValue(atAccountHolderEdit);
        $("#ownerpayments-0-account_type").selectOption("Savings");

        //клік по кнопці Submit фрейм Update owner
        $("#update_owner_send").click();
        $("#update_owner").shouldNotBe(visible, Duration.ofSeconds(20));

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("Owner sucessfully updated"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        // *** Перевіряє відредаговані дані Owner фрайм view owner ***

        //перевіряє відредагованого Owner в списку Owners
        $("input[name='OwnersSearch[name]']").shouldBe(visible, Duration.ofSeconds(10)).setValue(atOwnerName).pressEnter();
        $("#ownerssearch-owners_asset").selectOption("All");

        //клік по кнопці око
        $(".owners-td span.glyphicon-eye-open").click();
        $("#view_owner").shouldBe(visible);

        //перевіряє відредаговані дані Owner
        $("#view_owner").shouldBe(visible, Duration.ofSeconds(10));

        $("table#w0").$$("tr").findBy(text("Type"))
                .$$("td").first().shouldHave(text("Company"));

        $("table#w0").$$("tr").findBy(text("Company Name"))
                .$$("td").first().shouldHave(text(atCompanyNameEdit));

        $("table#w0").$$("tr").findBy(text("Street 1"))
                .$$("td").first().shouldHave(text(atStreet1Edit));

        $("table#w0").$$("tr").findBy(text("Street 2"))
                .$$("td").first().shouldHave(text(atStreet2Edit));

        $("table#w0").$$("tr").findBy(text("City"))
                .$$("td").first().shouldHave(text(atCityEdit));

        $("table#w0").$$("tr").findBy(text("State"))
                .$$("td").first().shouldHave(text(atStateEdit));

        $("table#w0").$$("tr").findBy(text("Zip"))
                .$$("td").first().shouldHave(text(String.valueOf(atZipEdit)));

        $("table#w0").$$("tr").findBy(text("Owner Name"))
                .$$("td").first().shouldHave(text(atOwnerName));

        $("table#w0").$$("tr").findBy(text("Phone"))
                .$$("td").first().shouldHave(text(atPhoneEdit));

        $("table#w0").$$("tr").findBy(text("Email"))
                .$$("td").first().shouldHave(text(atEmailEdit));

        $("table#w0").$$("tr").findBy(text("Birthday"))
                .$$("td").first().shouldHave(text("(not set)"));

        $("table#w0").$$("tr").findBy(text("Tax ID"))
                .$$("td").first().shouldHave(text(atTaxIdEdit));

        $("table.owner_payment_view").$$("tr").findBy(text("Account Holder"))
                .$$("td").first().shouldHave(text(atAccountHolderEdit));

        $("table.owner_payment_view").$$("tr").findBy(text("Bank Name"))
                .$$("td").first().shouldHave(text(atBankNameEdit));

        $("table.owner_payment_view").$$("tr").findBy(text("Account type"))
                .$$("td").first().shouldHave(text("Savings"));

        $("table.owner_payment_view").$$("tr").findBy(text("Routing Number"))
                .$$("td").first().shouldHave(text("988899994"));

        $("table.owner_payment_view").$$("tr").findBy(text("Account Number"))
                .$$("td").first().shouldHave(text("988899995"));

        $("#view_owner button.close").click();
        $("#view_owner").shouldNotBe(visible, Duration.ofSeconds(10));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }

}
