package web.bigTruck.smoke.owner;

import com.codeborne.selenide.SelenideElement;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalGenerateName;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static org.assertj.core.api.Assertions.assertThat;

public class WBS024_OwnerDell {

    // Click Up:
    // CRM SEMI Truck
    // Owners
    // 3. Удаление(admin only)

    String globalName = GlobalGenerateName.globalName();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void dell() {

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

        // *** Вкладка General фрейму Add owner ***

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
        rowOwner.shouldBe(text(atCompanyName));
        rowOwner.shouldBe(text(atOwnerName), Duration.ofSeconds(20));

        //клік по кнопці три крапки вибір Update
        $(".owners-td button.dropdown-toggle").click();
        $(".dropdown-menu-right .dd-icon-delete")
                .shouldBe(visible)
                .shouldBe(enabled)
                .shouldBe(clickable)
                .click();

        //popap підтвердження видалення
        String popapText = switchTo().alert().getText();

        assertThat(popapText).isEqualTo("Are you sure you want to delete this item?");
        switchTo().alert().accept();

        //перевіряє видалення Owner
        $("input[name='OwnersSearch[name]']").shouldBe(visible, Duration.ofSeconds(10)).setValue(atOwnerName).pressEnter();
        $("#ownerssearch-owners_asset").selectOption("All");
        $(".empty").shouldBe(text("No results found."));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
