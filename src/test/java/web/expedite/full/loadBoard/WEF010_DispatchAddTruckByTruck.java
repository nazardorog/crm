package web.expedite.full.loadBoard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalConfig;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WEF010_DispatchAddTruckByTruck {

    // Click Up:
    // CRM EXPEDITE - Full - LoadBoard
    // 7. Создание New Load / dispatch load \ добавление трака на груз через поле трак

    @Test
    public void addTruckByTruck() {

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

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
        File file = new File("C:/Empire/pdf1.pdf");
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

        // Dispatch board
        $("#load_dispatch").shouldBe(visible,EXPECT_GLOBAL);
        $("#select2-load_truck_id-0-container").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".select2-search__field").shouldBe(enabled).setValue(atTruck);
        $(".select2-results__option--highlighted").shouldHave(text(atTruck)).click();
        $("#select2-load_driver_id-0-container").shouldHave(Condition.text(atDriver));
        $("#select2-load_team_driver_id-0-container").shouldHave(Condition.text(atTeamDriver));
        $("#select2-load_truck_id-0-container").shouldHave(Condition.text(atTruck));

        // Remove help block
        boolean helpBlock = $(".help-block").isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        // Get load number
        String dispatchLoad = $("#load_dispatch .modal-title").getText();
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();

        // Close Dispatch board
        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible,EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Load dispatch sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Check creation load
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("#main-loads-grid-filters").shouldBe(visible, EXPECT_GLOBAL);
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();

        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoad.shouldHave(text(atTruck));
        rowLoad.shouldHave(text(atDriver));
        rowLoad.shouldHave(text(atTeamDriver));
        rowLoad.shouldHave(text(atBroker));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
