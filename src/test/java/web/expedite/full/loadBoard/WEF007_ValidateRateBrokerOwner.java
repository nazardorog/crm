package web.expedite.full.loadBoard;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WEF007_ValidateRateBrokerOwner {

    @Test
    public void rateBrokerOwner() throws InterruptedException {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

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
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

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

        //pallets shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets destination
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        $("#loads-reference").setValue("1122334");

        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll down
        $("#add_load_send_old").click();

        executeJavaScript("arguments[0].scrollTop = 0;", modal); //scroll up

        // Customers Rate не може бути 0
        $(".field-loads-rate .help-block") //Customer Rate
                .shouldHave(Condition.visible)
                .shouldHave(Condition.text("Customers Rate cannot be blank."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
        $("#loads-rate")
                .shouldHave(Condition.cssValue("border-color", "rgb(221, 75, 57)"));

        // Carrier/Driver Rate може бути 0
        $("#loads-carrier_rate").
                shouldHave(Condition.cssValue("border-color", "rgb(0, 166, 90)"));
        $(".field-loads-carrier_rate .help-block")
                .shouldNotBe(Condition.visible);

        $("#loads-rate-disp").setValue("80000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("100000").pressEnter();

        //Rate Customers може бути менший Rate Carrier/Driver але з massage
        $("#loads-rate")
                .shouldHave(Condition.cssValue("border-color", "rgb(0, 166, 90)"));
        $(".Method… .help-block")
                .shouldNotBe(Condition.visible);

        $(".field-loads-carrier_rate .help-block")
                .shouldHave(Condition.visible)
                .shouldHave(Condition.text("Carrier/Driver Rate should be less Customers Rate. If you're not mistaken, ignore the warning."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
        $("#loads-carrier_rate")
                .shouldHave(Condition.cssValue("border-color", "rgb(221, 75, 57)"));


        //load file
        Thread.sleep(4000);
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load").find(".modal-footer-button .fa-files-o").click();

        executeJavaScript("arguments[0].scrollTop = 0;", modal);
        File file = new File("C:/Empire/pdf1.pdf");
        $("#loaddocuments-0-file").uploadFile(file);

        if (!$("#loaddocuments-0-type").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#loaddocuments-0-type"));
        }
        $("#loaddocuments-0-type").selectOption("BOL");

        if (!$("#load_documents_modal_pseudo_submit").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#load_documents_modal_pseudo_submit"));
        }
        $("#load_documents_modal_pseudo_submit").click();

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#add_load_send_old").click();

                $("#select2-load_truck_id-0-container")
                .shouldBe(visible, Duration.ofSeconds(20))
                .click();

        //dispatch board
        $(".select2-search__field").setValue("0303");
        $(".select2-results__option--highlighted").shouldHave(text("0303")).click();

        //приховуємо help блок
        SelenideElement helpBlock = $(".help-block");
        executeJavaScript("arguments[0].style.display='none';", helpBlock);

        if (!$("#loads-load_type label").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#loads-load_type label"));
        }
        $$("#loads-load_type label").findBy(Condition.text("Board")).click();

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
                $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(10));
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
