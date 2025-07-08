package utilsWeb.createDataExp;

import com.codeborne.selenide.Condition;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalLogin;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WCD005_Load {

    public String atBroker, atBrokerAgent, atShippersOrigin, atShippersDist, atReferenceNumber, atCustomersRate, atCarrierDriverRate,
            atTruck, atDriver, atTeamDriver, atOwner, loadNumber, atShippersOriginName, atShippersDistName;

    @Test
    public void runTest() {
        create();
    }

    public WCD005_Load create() {

        // Login
        GlobalLogin.login("exp_disp1");

        // [Chat widget] Remove
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // [Notifications sms] Remove
        boolean notifications = $("#sms-notifications-movement-body").isDisplayed();
        if (notifications){
            executeJavaScript("document.querySelector('#sms-notifications-movement-body').style.display='none'");
        }

        // Data for creating a Load
        this.atBroker = "at_Broker1";
        this.atBrokerAgent = "Auto test agent ";
        this.atShippersOrigin = "Auto test shipper 1";
        this.atShippersOriginName = "Kansas City, MO 64110";
        this.atShippersDist = "Auto test shipper 2";
        this.atShippersDistName = "New York, NY 10002";
        this.atReferenceNumber = "10000000";
        this.atCustomersRate = "100000";
        this.atCarrierDriverRate = "80000";
        this.atTruck = "0303";
        this.atDriver = "Auto Test";
        this.atTeamDriver = "Auto Test2";
        this.atOwner = "Autotest 1 Owner";

        // Create new load
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();

        // Broker
        $("#loads-form-create").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(clickable).click();
        $(".select2-search__field").setValue(this.atBroker);
        $$(".select2-results__options").findBy(text(this.atBroker)).click();
        $$("select#loads-agent_id option").findBy(text(this.atBrokerAgent)).click();

        // Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue(this.atShippersOrigin);
        $$("li.select2-results__option").findBy(text(this.atShippersOrigin)).click();

        // Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue(this.atShippersDist);
        $$("li.select2-results__option").findBy(text(this.atShippersDist)).click();

        // Calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateToday();

        // Calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateToday();

        // Calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateToday();

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateToday();

        // Pallets Origin Shippers
        $("#loadspickuplocations-0-weight").setValue("1");

        // Pallets Destination Shippers
        $("#loadsdeliverylocations-0-weight").setValue("1");

        // Input other data
        $("#loads-reference").setValue(this.atReferenceNumber);
        $("#loads-rate-disp").setValue(this.atCustomersRate).pressEnter();

        // Download file
        File file = new File(downloadsFolder + "1pdf.pdf");
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        $("#load_documents_modal").shouldBe(visible, EXPECT_GLOBAL);
        $("#loaddocuments-0-file").uploadFile(file);
        $("#loaddocuments-0-type").selectOption("POD");

        // Клік Apply фрейму додавання файлів
        $("#load_documents_modal_pseudo_submit").click();

        // Клік по кнопці Submit на фрейм New Load
        $("#add_load_send_old").click();

        // Dispatch board
        $("#load_dispatch").shouldBe(visible, EXPECT_GLOBAL);

        // Отримує номер вантажу
        String dispatchLoad = $("#load_dispatch .modal-title").shouldBe(visible, EXPECT_GLOBAL).getText();
        this.loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        // Dispatch board. Водить Truck
        $("#select2-load_truck_id-0-container").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".select2-search__field").setValue(this.atTruck);
        $$("li.select2-results__option").findBy(text(this.atTruck)).click();
        $("#select2-load_truck_id-0-container").shouldHave(text(this.atTruck), EXPECT_GLOBAL);

        // Remove help block
        boolean helpBlock = $(".help-block").shouldBe(visible, EXPECT_5).isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        // Remove chat widget
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // Remove sms notifications;
        if (notifications){
            executeJavaScript("document.querySelector('#sms-notifications-movement-body').style.display='none'");
        }

        // Dispatch Load Type. Close frame Dispatch
        $$("#loads-load_type label").findBy(Condition.text("Board")).click();
        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        Message.dellToast();

        // Close web driver
        CloseWebDriver.tearDown();

        return this;
    }
}
