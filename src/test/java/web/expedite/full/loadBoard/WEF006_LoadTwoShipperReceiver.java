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
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class WEF006_LoadTwoShipperReceiver {

    @Test
    public void twoShipperReceiver() throws InterruptedException {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        //Brockers
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $(".select2-results__options").shouldHave(text("Auto test broker")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent")).click();

        //Origin Shippers 1
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 1"))
                .click();

        //calendar Origin Shippers Date from 1
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(1);

        //calendar Origin Shippers Date to 1
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(2);

        //pallets shippers 1
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //Origin Shippers 2
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //calendar Origin Shippers Date from 2
        $("#loadspickuplocations-1-date_from-datetime .kv-datetime-picker").shouldBe(enabled, Duration.ofSeconds(5)).click();
        Calendar.setDateTime(1);

        //calendar Origin Shippers Date to 2
        $("#loadspickuplocations-1-date_to-datetime .kv-datetime-picker").shouldBe(enabled, Duration.ofSeconds(5)).click();
        Calendar.setDateTime(2);

        //pallets shippers 2
        $("#loadspickuplocations-1-weight").setValue("2");
        $("#loadspickuplocations-1-pallets").setValue("2");
        $("#loadspickuplocations-1-pcs").setValue("2");

        //Destination Shippers 1
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 3");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 3"))
                .click();

        //calendar Destination Shippers Date from 1
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled, Duration.ofSeconds(5)).click();
        Calendar.setDateTime(3);

        //calendar Destination Shippers Date to 1
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled, Duration.ofSeconds(5)).click();
        Calendar.setDateTime(4);

        //pallets shippers destination 1
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //Destination Shippers 2
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 4");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 4"))
                .click();

        //calendar Destination Shippers Date from 2
        $("#loadsdeliverylocations-1-date_from-datetime .kv-datetime-picker").shouldBe(enabled, Duration.ofSeconds(5)).click();
        Calendar.setDateTime(3);

        //calendar Destination Shippers Date to 2
        $("#loadsdeliverylocations-1-date_to-datetime .kv-datetime-picker").shouldBe(enabled, Duration.ofSeconds(5)).click();
        Calendar.setDateTime(4);

        //pallets shippers destination 2
        $("#loadsdeliverylocations-1-pallets").setValue("2");
        $("#loadsdeliverylocations-1-weight").setValue("2");
        $("#loadsdeliverylocations-1-pcs").setValue("2");

        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //load file
        Thread.sleep(4000);
        SelenideElement modal = $("#add_load");
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

        boolean statusDriver = $("#set-automatic-status-link").isDisplayed();
        if (statusDriver){
            $("#set-automatic-status-link").shouldHave(text("please set the automatic status change to Available On")).click();
            $(".text-set-status-link").shouldHave(text("Please note that the pick-up for this load is scheduled for  Thursday. As a result, the truck's status will automatically change to 'Available On' in the delivery city at 12:01 AM on the pick-up day."));
            $("#automatic_status_send]").click();
            $("#dispatch_load_send").click();
        }
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
