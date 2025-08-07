package web.bigTruck.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import org.testng.annotations.*;

import java.io.File;
import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS005_LoadDeliveredToInvoiced {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 5. Перевод груза с dеlivеrеd в load invoicеd

    @Test
    public void deliveredToInvoiced() throws InterruptedException{

        // Login
        GlobalLogin.login("bt_disp1");

        //створює новий вантаж
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").shouldBe(visible).setValue("Auto test broker");
        $$(".select2-results__options")
                .findBy(text("Auto test broker"))
                .click();
        $("#select2-broker-agent-load-select-container").click();
        $(".select2-search__field").setValue("Auto test agent");
        $$(".select2-results__options")
                .findBy(text("Auto test agent"))
                .click();

        //input other data
        Random random = new Random();
        String reference = String.format("%3d", random.nextInt(10000000));
        String commodity = String.format("%3d", random.nextInt(10000000));
        $("#loads-reference").setValue(reference);
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-load_miles").setValue("200");
        $("#loads-commodity_big_trucks_note").setValue(commodity);
        $("#select2-booked_with-container").shouldHave(text("Auto 2Test BT"));
        $("#loads-commodity").setValue("Text Commodity Notes");
        $$("div#loads-check_full_load label").findBy(text("FTL")).click();
        $$("#loads-local_type label").findBy(text("Local")).click();
        $$("#loads-load_type label").findBy(text("Board")).click();

        //load file
        $(".load_documents_counter-flex").click();
        $("#select2-loaddocuments-0-type-container").click();
        $$(".select2-results__option").findBy(text("Rate confirmation")).click();

        String filePath;
        if (new File("/.dockerenv").exists()) {
            filePath = "/app/Empire/1pdf.pdf";  // для Docker
        } else {
            filePath = "C:\\Empire\\1pdf.pdf";  // для локально
        }
        File file = new File(filePath);

        $("#loaddocuments-0-file").uploadFile(file);
        $("#load_documents_modal_pseudo_submit").click();

        //вкладка Origin & Destination
        $("#origin-destination-tab").click();

        //Origin Shippers
        $("#select2-shippers-receiver-origin-container").shouldBe(enabled).click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 1"))
                .click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").shouldBe(enabled).click();
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

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        $("#info-tab").click();

        //клік по кнопці "Submit & Dispatch" на фрейм New Load
        $("#add_load_send_dispatch").click();

        //dispatch board
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#view_load").shouldBe(text("Dispatch #"));

        //отримує номер вантажу
        String loadNumber = $("#view_load .check_call_pro").getText();

        //клік add Driver
        $("a[title='Add Driver'] .glyphicon.icon-plus-load").click();

        //вибирає Carrier
        $("#select2-carrierId-container").click();
        $$(".select2-results__option").findBy(text("AutoTestOwner1 INC")).click();
        $("#select2-carrierId-container").shouldHave(text("AutoTestOwner1 INC"));

        //вибирає Truck
        $("#select2-trucks-template-container").click();
        $(".select2-search__field").setValue("0305");
        $$(".select2-results__option").findBy(text("0305 (AutoTestOwner1 INC)")).click();
        $("#select2-trucks-template-container").shouldHave(text("0305 (AutoTestOwner1 INC)"));

        //вибирає Driver
        $("#select2-load_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver3 Big Truck")).click();
        $("#select2-load_driver_id-container").shouldHave(text("Auto Test Driver3 Big Truck"));

        //вибирає Team Driver
        $("#select2-load_team_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver4 Big Truck")).click();
        $("#select2-load_team_driver_id-container").shouldHave(text("Auto Test Driver4 Big Truck"));

        //вибирає Trailer
        $("#select2-trailer_id-create-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("AutoTest Trailer1")).click();
        $("#select2-trailer_id-create-container").shouldHave(text("AutoTest Trailer1"));

        //вибирає Location From вводить Location To
        $("#loadexpenses-location").selectOption("Kansas City, MO 64110");
        $("#loadexpenses-location_to").setValue("New York, NY 10002");

        //вибирає Start Date
        executeJavaScript("document.querySelector('#loadexpenses-start_date-datetime .kv-datetime-picker').click()");
        Calendar.setDateTime(0);

        //клік по Submit фрейм Add driver
        $("#update_load_driver_send").click();
        $("#add_driver").shouldNotBe(visible, Duration.ofSeconds(20));

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //*** Переводить вантаж на вкладку Loads Delivered ***
        //в Load Board знаходить створений вантаж
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));
        $("#loadsdeliverylocations-date_delivery").shouldNotBe(visible);

        //відкриває Pick Info
        $(".col-origin .view_pick_up_location").shouldBe(visible).click();
        $("#view_item .modal-title").shouldBe(visible, Duration.ofSeconds(5));

        //Pick Info вводить calendar Check In
        $("#loadspickuplocations-date_check_in-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // Add BOL
        $("button.add-document").click();
        $("#bol_documents").shouldBe(visible, EXPECT_GLOBAL);
        File fileBol = new File(downloadsFolder + "1pdf.pdf");
        $("#loaddocuments-0-file").uploadFile(fileBol);
        $("#loaddocuments-0-description").setValue("Description");
        $("#bol_documents_send").click();
        $("#bol_documents").shouldNotBe(visible, EXPECT_GLOBAL);

        // [Toast] Check message
        Message.checkToast("Load documents update sucessfully");

        // Add Seal Pictures
        $("button.seal-add-btn").click();
        $("#seal_pictures_documents").shouldBe(visible, EXPECT_GLOBAL);
        String fileNameJpeg = "4jpeg.jpg";
        File fileJpeg = new File(downloadsFolder + fileNameJpeg);
        $("#loaddocuments-0-file").uploadFile(fileJpeg);
        sleep(10000);
        $("#loaddocuments-0-description").setValue("Description");
        $("#loaddocuments-0-seal_number").setValue("Seal");
        $("#loaddocuments-0-seal_position").selectOption("Left");
        $("#seal_pictures_documents_send").click();
        $("#seal_pictures_documents").shouldNotBe(visible, EXPECT_GLOBAL);

        // [Toast] Check message
        Message.checkToast("Load documents update sucessfully");

        //Pick Info вводить calendar Next STOP ETA
        $("#loadspickuplocations-eta_to_destination-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(0);

        //Pick Info вводить calendar Check Out
        $("#loadspickuplocations-date_check_out-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // закриває модальне вікно Pick Info
        $("#view_item .close").click();

        //відкриває Drop Info
        $("#loadsdeliverylocations-date_delivery").shouldNotBe(visible);
        $(".col-destination .view_delivery_location").shouldBe(visible).click();
        $("#view_item .modal-title").shouldBe(visible, Duration.ofSeconds(5));
        $("#view_item .loads-delivery-view").shouldBe(visible, Duration.ofSeconds(5));

        //Drop Info встановлює Check In
        $("#loadsdeliverylocations-date_check_in-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(1);

        // Add POD
        $("button.add-document").click();
        $("#pod_picture_documents").shouldBe(visible, EXPECT_GLOBAL);
        String filePodPictures = "1pdf.pdf";
        File filePod = new File(downloadsFolder + filePodPictures);
        sleep(10000);
        $("#loaddocuments-0-file").uploadFile(filePod);
        $("#loaddocuments-0-description").setValue("Description");
        $("#pod_picture_documents_send").click();
        $("#pod_picture_documents").shouldNotBe(visible, EXPECT_GLOBAL);

        // [Toast] Check message
        Message.checkToast("Load documents update sucessfully");

        // Check box No seal
        $("#not_have_seal").click();

        //Drop Info встановлює Date delivery
        $("#loadsdeliverylocations-date_delivery-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(1);

        //очікує модальне вікно Load Delivered for Trip
        $("#load_delivered").shouldBe(visible, EXPECT_GLOBAL);
        $("#load_delivered .close").click();
        $("#load_delivered").shouldNotBe(visible, EXPECT_GLOBAL);

        //закриває модальне вікно Drop Info
        $("#view_item .close").click();
        $("#view_item").shouldNotBe(visible, Duration.ofSeconds(10));

        //клік редагування вантажу клік "Documents"
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0);
        rowLoad.shouldHave(text(loadNumber));
        rowLoad.$("button.dropdown-toggle").shouldBe(clickable).click();
        rowLoad.$(".btn-group").shouldHave(Condition.cssClass("open"), Duration.ofSeconds(20));
        rowLoad.$$(".dropdown-menu-right li").findBy(text("Documents")).shouldBe(enabled).click();

        //додає документ Preview load
        $("#load_documents").shouldBe(visible, EXPECT_GLOBAL);
        $("button.add-document").click();
        String filePreviewLoad = "5jpeg.jpg";
        File filePreview = new File(downloadsFolder + filePreviewLoad);
        sleep(10000);
        $("#loaddocuments-1-file").uploadFile(filePreview);
        $("#loaddocuments-1-type").selectOption("Preview load");
        sleep(10000);
        $("#load_documents_send").click();

        // [Toast] Check message
        Message.checkToast("Load documents update sucessfully");

        //клік редагування вантажу клік "Mark as delivered"
        SelenideElement rowLoadMark = $$("table.table-striped tbody tr").get(0);
        rowLoadMark.shouldHave(text(loadNumber));
        rowLoadMark.$("button.dropdown-toggle").shouldBe(clickable).click();
        rowLoadMark.$(".btn-group").shouldHave(Condition.cssClass("open"), Duration.ofSeconds(20));
        rowLoadMark.$$(".dropdown-menu-right li").findBy(text("Mark as delivered")).shouldBe(enabled).click();

        //popap підтвердження переведення в Loads Delivered
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Are you sure you want to Mark as delivered this load?");
        switchTo().alert().accept();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(20)).shouldHave(text("Load Delivered successfully"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє що вантаж відображається на Loads Delivered
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Delivered")).click();
        $("#delivered input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#delivered-loads-grid a.view_load").shouldHave(text(loadNumber));

        //*** Переводить вантаж з Loads Delivered в Load Invoiced ***
        Thread.sleep(1000);
        $("#delivered-loads-grid .dropdown-toggle").shouldBe(enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Mark as invoiced")).shouldBe(enabled, Duration.ofSeconds(10)).click();

        $("#mark_as_invoiced .modal-header").shouldBe(visible, Duration.ofSeconds(5)).shouldHave(text("Mark as invoiced"));
        $("#mark_as_invoiced_apply").shouldBe(enabled).click();

        //перевіряє що вантаж не відображається на Loads Delivered
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Delivered")).click();
        $("#delivered input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#delivered-loads-grid .empty").shouldHave(text("No results found."));

        //перевіряє що вантаж вже відображається на Loads Invoiced
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Invoiced")).click();
        $("#invoiced input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#invoice-loads-grid a.view_load").shouldHave(text(loadNumber));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
