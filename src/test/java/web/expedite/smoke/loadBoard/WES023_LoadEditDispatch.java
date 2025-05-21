package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.CloseWebDriver;


import utilsWeb.configWeb.GlobalLogin;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES023_LoadEditDispatch {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 12. Actions / Edit Dispatch

    @Test
    public void editDispatch() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Create new load
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // Data for creating a Load
        final String atBroker = "at_Broker1";
        final String atBrokerAgent = "Auto test agent ";
        final String atOwner = "Autotest 1 Owner ";
        final String atShippersOrigin = "Auto test shipper 1";
        final String atShippersDist = "Auto test shipper 2";
        final String atReferenceNumber = "10000000";
        final String atCustomersRate = "100000";
        final String atCarrierDriverRate = "80000";
        final String atTruck = "0303";
        final String atDriver = "Auto Test";
        final String atTeamDriver = "Auto Test2";

        // Broker
        $("#loads-form-create").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(clickable).click();
        $(".select2-search__field").setValue(atBroker);
        $$(".select2-results__options").findBy(text(atBroker)).click();
        $$("select#loads-agent_id option").findBy(text(atBrokerAgent)).click();

        // Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue(atShippersOrigin);
        $$("li.select2-results__option").findBy(text(atShippersOrigin)).click();

        // Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue(atShippersDist);
        $$("li.select2-results__option").findBy(text(atShippersDist)).click();

        // Calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        // Calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        // Pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        // Pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        // Input other data
        $("#loads-reference").setValue(atReferenceNumber);
        $("#loads-rate-disp").setValue(atCustomersRate).pressEnter();
        $("#loads-carrier_rate-disp").setValue(atCarrierDriverRate).pressEnter();

        // Download file
        File file = new File(downloadsFolder + "/1pdf.pdf");
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
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        // Водить Truck
        $("#select2-load_truck_id-0-container").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".select2-search__field").setValue(atTruck);
        $$("li.select2-results__option").findBy(text(atTruck)).click();
        $("#select2-load_truck_id-0-container").shouldHave(text(atTruck), EXPECT_GLOBAL);

        // Remove help block
        boolean helpBlock = $(".help-block").shouldBe(visible, EXPECT_5).isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        // Dispatch Load Type. Close frame Dispatch
        $$("#loads-load_type label").findBy(Condition.text("Board")).click();
        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Load dispatch sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє дані створеного вантажу
        $(".content-header").shouldHave(text("Load Board"));
        $("#main-loads-grid-filters").shouldBe(visible, EXPECT_GLOBAL);

        // Перевіряє створений Load в таблиці
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoad.$("td a.view_load").shouldHave(text(loadNumber));
        rowLoad.$("td a.view_truck").shouldHave(text(atTruck));
        rowLoad.$$("td a.view_driver").get(0).shouldHave(text(atDriver));
        rowLoad.$$("td a.view_driver").get(1).shouldHave(text(atTeamDriver));
        rowLoad.$("td a.view_owner").shouldHave(text(atOwner));
        rowLoad.$("td a.view_broker").shouldHave(text(atBroker));
        rowLoad.$("td a.view_pick_up_location").shouldHave(text("Kansas City, MO 64110"));
        rowLoad.$$(".loads-locations").get(0).shouldHave(text("Wt 1 Plt 1 Pcs 1"));
        rowLoad.$("td a.view_delivery_location").shouldHave(text("New York, NY 10002"));
        rowLoad.$$(".loads-locations").get(1).shouldHave(text("Wt 1 Plt 1 Pcs 1"));

        // Редагування вантажу через Dispatch
        rowLoad.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowLoad.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        rowLoad.$$(".dropdown-menu-right li").findBy(text("Edit Dispatch")).click();

        // Data for edit a Load
        final String atTruckEdit = "0304";
        final String atDriverEdit = "AutoTest Driver2";
        final String atBookedWithEdit = "Auto Test Disp 2";
        final String atLoadTypeEdit = "Warm";
        final String atNotesPublicEdit = "Notes Public 2";
        final String atUser1Edit = "auto test tracker 1";
        final String atUser2Edit = "Auto Test user2";
        final String atTeamDriverEdit = "Auto Test2";

        // Edit Dispatch load
        $("#load_dispatch").shouldBe(visible, EXPECT_GLOBAL);

        // Edit Dispatch водить Truck
        $("#select2-load_truck_id-0-container").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".select2-search__field").setValue(atTruckEdit);
        $$("li.select2-results__option").findBy(text(atTruckEdit)).click();
        $("#select2-load_truck_id-0-container").shouldHave(text(atTruckEdit), EXPECT_GLOBAL);

        // Edit Dispatch Remove help block
        boolean helpBlockEdit = $(".help-block").shouldBe(visible, EXPECT_5).isDisplayed();
        if (helpBlockEdit){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        // Edit Dispatch перевіряє Driver
        $("#select2-load_driver_id-0-container").shouldHave(Condition.text(atDriverEdit));

        // Edit Dispatch Booked With
        $("#loads-booked_with").selectOption(atBookedWithEdit);
        $("#loads-booked_with").getSelectedOption().shouldHave(text(atBookedWithEdit));

        // Edit Dispatch Load Type
        $("#loads-load_type input[value='1']").shouldBe(Condition.selected);
        $$("#loads-load_type label").findBy(Condition.text(atLoadTypeEdit)).click();
        $("#loads-load_type input[value='2']").shouldBe(Condition.selected);

        // Edit Dispatch Notes Public
        $("#loadsdetail-notes_public").setValue(atNotesPublicEdit);

        // Edit Assign user add two
        $("#loadassignedusers-user_id").selectOption("Auto Test user2");
        $("#loadassignedusers-user_id").getSelectedOption().shouldHave(text("Auto Test user2"));
        $("#load_assigned_users_send").click();
        $$("table.table-assigned-users tbody tr").get(2).shouldHave(text("Auto Test user2"));

        // Click Submit frame Dispatch load
        $("#dispatch_load_send").click();

        // Driver automatic status change to Available On
        boolean statusDriver = $("#set-automatic-status-link").shouldBe(visible, EXPECT_5).isDisplayed();
        if (statusDriver){
            $("#set-automatic-status-link").shouldHave(text("please set the automatic status change to Available On")).click();
            $("#automatic-status-modal").shouldBe(visible, EXPECT_GLOBAL);
            $(".text-set-status-link").shouldHave(matchText("Please note that the pick-up for this load is scheduled for\\s+.*\\. As a result, the truck's status will automatically change to 'Available On' in the delivery city at 12:01 AM on the pick-up day\\."));
            $("#automatic_status_send").click();

            // Toast massage
            $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
            $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Status will be automatically change."));
            $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

            // Close frame Dispatch load
            $("#load_dispatch").shouldBe(visible, EXPECT_GLOBAL);
            $("#dispatch_load_send").click();

            // Toast massage
            $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
            $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Load dispatch sucessfully added"));
            $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);
        }

        $("#load_dispatch").shouldNotBe(visible, EXPECT_GLOBAL);

        // Edit check data Load board
        $("input[name='LoadsSearch[our_pro_number]']").shouldHave(value(loadNumber));
        SelenideElement rowLoadEdit = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoadEdit.$("td a.view_load").shouldHave(text(loadNumber));
        rowLoadEdit.$("td a.view_truck").shouldHave(text(atTruckEdit));
        rowLoadEdit.$$("td a.view_driver").get(0).shouldHave(text(atDriverEdit));
        $(".edited-model-field").shouldHave(text(atNotesPublicEdit));

        // Edit load Dispatch load
        rowLoadEdit.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowLoadEdit.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        rowLoadEdit.$$(".dropdown-menu-right li").findBy(text("Edit Dispatch")).click();

        // Edit Dispatch load check data. Close frame Dispatch load
        $("#load_dispatch").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-load_truck_id-0-container").shouldHave(text(atTruckEdit));
        $("#select2-load_driver_id-0-container").shouldHave(text(atDriverEdit));
        $("#loads-booked_with").shouldHave(text(atBookedWithEdit));
        $$("label").findBy(text("Warm")).$("input").shouldBe(Condition.selected);
        $("#loads-load_type input[value='2']").shouldBe(Condition.selected);
        $$("table.table-assigned-users tbody tr").get(1).shouldHave(text(atUser1Edit));
        $$("table.table-assigned-users tbody tr").get(2).shouldHave(text(atUser2Edit));
        $("#load_dispatch button.close").click();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
