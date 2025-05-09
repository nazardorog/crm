package web.expedite.full.loadBoard;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalConfig;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WEF009_ValidatePltWtPcs {

    @Test
    public void pltWtPcs(){

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $$(".select2-results__options")
                .findBy(text("Auto test broker"))
                .click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent ")).click();

        //Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 1"))
                .click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //pallets shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets destination
        $("#loadsdeliverylocations-0-pallets").setValue("2");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //скрол, клік Submit
        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();

        //валідність Plts
        $(".load_pallets_error").shouldHave(Condition.text("The Pallets of the shipment is not equal to the Pallets of the destination:1/2"));

        //скрол вверх
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //болдер чи став тільки червоним Original Plts
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        //болдер чи став тільки червоним Destination Plts
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        //валідність Weight
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("2");

        //скрол вниз
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();
        $(".load_pallets_error").shouldHave(Condition.text("The weight of the shipment is not equal to the weight of the destination: 1/2"));

        //скрол вверх
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //болдер чи став тільки червоним Original Weight
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        //болдер чи став тільки червоним Destination Weight
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

    //валідність Pcs
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("2");

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();
        $$(".load_pallets_error").get(0).shouldHave(Condition.text("The total number of Pcs on pickup must be equal to the total number of Pcs on delivery:1/2"));

        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //болдер чи став тільки червоним Original Pcs
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldHave(Condition.cssClass("has-error"));

        //болдер чи став тільки червоним Destination Pcs
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
