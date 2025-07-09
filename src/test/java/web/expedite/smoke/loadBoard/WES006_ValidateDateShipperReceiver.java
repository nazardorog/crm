package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Listeners;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

@Listeners(utilsWeb.commonWeb.Listener.class)
public class WES006_ValidateDateShipperReceiver {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 5. Создание New Load / валидация полей даты в ПУ и ДЕЛ

    @Test
    public void dateShipperReceiver() {

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
        if (chatWidget) {
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
        Calendar.setDateTime(0);

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
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        // Field Reference Number, Customers Rate, Driver Rate
        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        // Load file
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        $("#load_documents_modal").shouldBe(visible, EXPECT_GLOBAL);
        String fileName = "1pdf.pdf";
        File file = new File(downloadsFolder + fileName);
        $("#loaddocuments-0-file").uploadFile(file);
        $("#loaddocuments-0-type").selectOption("BOL");

        // Scrolling form Load file
        if (!$("#load_documents_modal_pseudo_submit").isDisplayed()){
            Scrolling.scrollDown($("#add_load"), $("#load_documents_modal_pseudo_submit"));
        }
        $("#load_documents_modal_pseudo_submit").click();

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Перевірка валідності Origin Date To
        // Origin Date To
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Date To must be greater or equal to \"Date From\"."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));

        // Не відображається origin Date From help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // Не відображається destination Date From help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // Не відображається destination Date To help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // Перевірка валідності Destination Date From
        // Підготовка данних

        // Calendar Origin Shipper Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Calendar Destination Shipper Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Тест
        // Destination Date From
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Date from must be greater than the first Date From in Origin section"))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));

        // Не відображається origin Date From help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        //не відображається origin Date To help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        //не відображається destination Date To help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        //перевірка валідності Destination Date To
        //підготовка данних

        // Calendar Destination Shipper Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Destination Date To
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Date To must be greater or equal to \"Date From\"."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));

        // не відображається origin Date From help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // не відображається origin Date To help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // не відображається destination Date From help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        $("#add_load .close").click();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
