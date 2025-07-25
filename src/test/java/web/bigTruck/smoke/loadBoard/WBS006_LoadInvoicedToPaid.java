package web.bigTruck.smoke.loadBoard;

import com.codeborne.selenide.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS006_LoadInvoicedToPaid {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 6. Перевод груза с load invoicеd в loads paidd

    @Test
    public void invoicedToPaid() throws InterruptedException{

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

         File filePathUp = new File(Configuration.downloadsFolder + "1pdf.pdf");
        $("#loaddocuments-0-file").uploadFile(filePathUp);
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
        $(".kv-datetime-picker").click();
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

        //відкриває Drop Info
        $("#loadsdeliverylocations-date_delivery").shouldNotBe(visible);
        $(".col-destination .view_delivery_location").shouldBe(visible).click();
        $("#view_item .modal-title").shouldBe(visible, Duration.ofSeconds(5));
        $("#view_item .loads-delivery-view").shouldBe(visible, Duration.ofSeconds(5));

        //Drop Info встановлює Date delivery
        $("#loadsdeliverylocations-date_delivery-datetime .kv-datetime-picker").shouldBe(enabled).click();
        Calendar.setDateTime(1);

        //закриває модальне вікно Drop Info
        $("#view_item .close").click();
        $("#view_item").shouldNotBe(visible, Duration.ofSeconds(10));

        //в Load Board знаходить створений вантаж
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));

         //клік редагування вантажу клік "Mark as delivered"
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0);
        rowLoad.shouldHave(text(loadNumber));
        Selenide.sleep(2000);
        rowLoad.$("button.dropdown-toggle").shouldBe(clickable).click();
        rowLoad.$(".btn-group").shouldHave(Condition.cssClass("open"), Duration.ofSeconds(20));
        rowLoad.$$(".dropdown-menu-right li").findBy(text("Mark as delivered")).shouldBe(enabled).click();

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

        $("#delivered-loads-grid .dropdown-toggle").shouldBe(clickable, Duration.ofSeconds(20)).click();
        $$(".dropdown-menu-right li").findBy(text("Mark as invoiced")).shouldBe(enabled, Duration.ofSeconds(10)).click();

        $("#mark_as_invoiced .modal-header").shouldBe(visible, Duration.ofSeconds(10)).shouldHave(text("Mark as invoiced"));
        $("#mark_as_invoiced_apply").shouldBe(enabled).click();

        //перевіряє що вантаж не відображається на Loads Delivered
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Delivered")).click();
        $("#delivered input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#delivered-loads-grid .empty").shouldHave(text("No results found."));

        //перевіряє що вантаж відображається на Loads Invoiced
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Invoiced")).click();
        $("#invoiced input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#invoice-loads-grid a.view_load").shouldHave(text(loadNumber));

        //*** Переводить вантаж з Load Invoiced в Loads Paid ***

        //вкладка Loads Invoiced, ставить чек бокс в колонці Load pair
        $("#invoice-loads-grid .container-checkbox-green")
                .shouldBe(visible, enabled)
                .setSelected(true);

        //клік по батон Save вкладки Loads Invoiced
        $("#paid-bulk-btn")
                .shouldBe(visible, enabled)
                .click();

        //перевіряє що вантаж не відображається на Loads Invoiced
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Invoiced")).click();
        $("#invoiced input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#invoice-loads-grid .empty").shouldHave(text("No results found."));

        //перевіряє що вантаж відображається на Loads Paid
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads Paid")).click();
        $("#paid input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).setValue(loadNumber).pressEnter();
        $("#paid-loads-grid a.view_load").shouldHave(text(loadNumber));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
