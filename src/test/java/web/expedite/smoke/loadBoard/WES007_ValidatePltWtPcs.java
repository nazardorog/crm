package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.Listeners;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.jenkins.CustomName;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

@Listeners(utilsWeb.commonWeb.Listener.class)
@Epic("Expedite")
@Feature("Smoke")
public class WES007_ValidatePltWtPcs {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 6. Создание New Load / валидация поле подсчета plts \ wght \ pcs

    @Test
    public void pltWtPcs() {

        // Назва класу для Allure
        CustomName.getDescription();

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for creating a load
        String atBroker = "at_Broker1";
        String atAgent = "Auto test agent ";
        String atOriginShippers = "at_OriginShippers1";
        String atDestinationShippers = "at_DestinationShippers1";
        String atTruck = "0303";
        String atDriver = "Auto Test";
        String atTeamDriver = "Auto Test2";

        // Add load creation
        $("#new_load").shouldBe(enabled, Duration.ofSeconds(20)).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // Field Broker
        $("#loads-form-create").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue(atBroker);
        $$(".select2-results__options").findBy(text(atBroker)).click();

        // Field Agent Broker
        $("#loads-agent_id").shouldBe(visible, EXPECT_GLOBAL).click();
        $$("select#loads-agent_id option").findBy(text(atAgent)).click();

        // Field Origin Shipper
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue(atOriginShippers);
        $$("li.select2-results__option").findBy(text(atOriginShippers)).click();

        // Field Destination Shipper
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue(atDestinationShippers);
        $$("li.select2-results__option").findBy(text(atDestinationShippers)).click();

        // Calendar Origin Shipper Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        // Calendar Origin Shipper Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Calendar Destination Shipper Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        // Field Pallets Shipper
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        // Field Pallets Destination
        $("#loadsdeliverylocations-0-pallets").setValue("2");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        // Field Reference Number, Customers Rate, Driver Rate
        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Validation Plts
        $(".load_pallets_error").shouldHave(Condition.text("The Pallets of the shipment is not equal to the Pallets of the destination:1/2"));

        // Bolder чи став тільки червоним Original Plts
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        // Bolder чи став тільки червоним Destination Plts
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        // Validation Weight
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("2");

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Validation Wght
        $(".load_pallets_error").shouldHave(Condition.text("The weight of the shipment is not equal to the weight of the destination: 1/2"));

        // Bolder чи став тільки червоним Original Weight
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        // Bolder чи став тільки червоним Destination Weight
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        // Validation Pcs
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("2");

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Bolder чи став тільки червоним Original Pcs
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldHave(Condition.cssClass("has-error"));

        // Bolder чи став тільки червоним Destination Pcs
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldHave(Condition.cssClass("has-error"));

        $("#add_load .close").click();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
