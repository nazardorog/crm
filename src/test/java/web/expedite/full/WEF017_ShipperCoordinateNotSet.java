package web.expedite.full;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class WEF017_ShipperCoordinateNotSet {

    @Test
    public void coordinateNotSet() throws InterruptedException {

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
        $(".select2-search__field").setValue("AutoTestNotSet1");
        $(".select2-results").shouldHave(text("AutoTestNotSet1")).click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("AutoTestNotSet2");
        $(".select2-results__options").shouldHave(text("AutoTestNotSet2")).click();

        //перевіряє коректність вибору Destination Shippers
        $("#shippers-destination-sortable .shipper-locations-name").shouldHave(text("AutoTestNotSet2"));
        $("#shippers-destination-sortable .shipper-locations-street").shouldHave(text("AutoTest Street2"));
        $("#shippers-destination-sortable .shipper-locations-location").shouldHave(text("autoTest"));

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(1);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(2);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(3);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(4);

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //other data
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
        $("#add_load_send_old").shouldHave(visible).click();

//        $$(".load-error div").shouldHave(sizeGreaterThanOrEqual(2));
        $$(".load-error div")
                .get(0)
                .shouldHave(text("You cannot add this load because shipper-receiver’s address is not valid."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
        $$(".load-error div")
                .get(1)
                .shouldHave(exactText("You cannot add this load because shipper-receiver’s address is not valid."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
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
