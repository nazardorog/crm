package web.bigTruck.smoke.loadBoard;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS014_LoadCheckCallDell {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 14. Чек Колы - Удаление

    String agent = "Auto test agent";

    @Test
    public void checkCallDell () {

        // Login
        GlobalConfig.OPTION_LOGIN = "big";
        WebDriverConfig.setup();
        LoginHelper.login();

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
        $(".select2-search__field").setValue(agent);
        $$(".select2-results__options")
                .findBy(text("Auto test agent"))
                .click();

        //input other data
        Random random = new Random();
        String reference = String.format("%3d", random.nextInt(10000000));
        String commodity = String.format("%3d", random.nextInt(10000000));
        $("#loads-reference").setValue(reference);
        $("#loads-rate-disp").setValue("100000").pressEnter();
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

        // клік по кнопці "Submit & Dispatch" на фрейм New Load
        $("#add_load_send_dispatch").click();

        //dispatch board
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#view_load").shouldBe(text("Dispatch #"));

        // отримує номер вантажу
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

        // Тост вспливайка
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Driver successfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //перевіряє що вантаж відображається на Loads en Route
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));

        //відкриває Dispatch
        $("#main-loads-grid button.view_load").click();
        $("#view_load").shouldBe(visible).shouldHave(text("Dispatch #"));

        // Перевіряє що таблиця Check Calls пуста
        $(".table-dispatch-check-calls").shouldHave(text("No results found."));

        // Дані для створення CheckCalls
        // Дані new Check Call 1
        String noteCheckCalls1 = "Note Check cool 1";
        String tapeCheckCalls1 = "User";
        String zipCheckCalls1 = "77001";
        String locationCheckCalls1 = "Houston, TX";
        String userCheckCalls1 = "Auto 2Test BT";

        // Дані new Check Call 2
        String noteCheckCalls2 = "Note Check cool 2";
        String tapeCheckCalls2 = "Alert";
        String zipCheckCalls2 = "75216";
        String locationCheckCalls2 = "Dallas, TX";
        String userCheckCalls2 = "Auto 2Test BT";

        // Дані new Check Call 3
        String noteCheckCalls3 = "Note Check cool 3";
        String tapeCheckCalls3 = "LFB";
        String zipCheckCalls3 = "78213";
        String locationCheckCalls3 = "San Antonio, TX";
        String userCheckCalls3 = "Auto 2Test BT";

        // *** Додає Check Call 1 ***
        $(".dispatch-head-drivers a.check_call_load")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        $("#add_check_call_load")
                .shouldBe(visible);

        // Додає Call 1 поле Location
        $("#loadnotes-location")
                .setValue(zipCheckCalls1);
        $$("#autocomplete-results-loadnotes-location li")
                .findBy(text(locationCheckCalls1 + " " + zipCheckCalls1))
                .shouldBe(visible)
                .click();

        // new Call 1 поле Note
        $("#loadnotes-note")
                .setValue(noteCheckCalls1)
                .shouldBe(visible);

        // new Call 1 клік Submit фрейм Check Call
        $("#check_call_load_send")
                .click();

        // *** Додає Check Call 2 ***
        $(".dispatch-head-drivers a.check_call_load")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        $("#add_check_call_load")
                .shouldBe(visible);

        // new Call 2 поле Location
        $("#loadnotes-location")
                .setValue(zipCheckCalls2);
        $$("#autocomplete-results-loadnotes-location li")
                .findBy(text(locationCheckCalls2 + " " + zipCheckCalls2))
                .shouldBe(visible)
                .click();

        // new Call 2 поле Note
        $("#loadnotes-note")
                .setValue(noteCheckCalls2)
                .shouldBe(visible);

        // new Call 2 чек бокс Alert
        $("#loadnotes-alert")
                .click();

        // new Call 2 клік Submit фрейм Check Call
        $("#check_call_load_send")
                .click();

        // *** Додає Check Call 3 ***
        $(".dispatch-head-drivers a.check_call_load")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        $("#add_check_call_load")
                .shouldBe(visible);

        // new Call 3 поле Location
        $("#loadnotes-location")
                .setValue(zipCheckCalls3);
        $$("#autocomplete-results-loadnotes-location li")
                .findBy(text(locationCheckCalls3 + " " + zipCheckCalls3))
                .shouldBe(visible)
                .click();

        // new Call 3 поле Note
        $("#loadnotes-note")
                .setValue(noteCheckCalls3)
                .shouldBe(visible);

        // new Call 3 чек бокс LFB
        $("#loadnotes-location_for_broker")
                .click();

        // new Call 3 Сalendar ETA
        $("#add_eta-datetime .kv-datetime-picker")
                .click();
        Calendar.setDateTime(0);

        // new Call 3 клік Submit фрейм Check Call
        $("#check_call_load_send")
                .click();

        // Селектори для видалення
        SelenideElement rowCheckCalls1 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(noteCheckCalls1));
        SelenideElement rowCheckCalls2 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(noteCheckCalls2));
        SelenideElement rowCheckCalls3 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(noteCheckCalls3));

        // *** Видаляє Check Call 1 ***
        rowCheckCalls1.$(".glyphicon-trash").shouldBe(visible, enabled).click();

        // Check Call 1 попап діалог
        String popapText1 = switchTo().alert().getText();

        // Check Call 1 попап підтвердження видалення
        assertThat(popapText1).isEqualTo("Are you sure you want to delete this load note?");
        switchTo().alert().accept();

        // Check Call 1 перевірка що видалений
        rowCheckCalls1.shouldNot(exist);

        // *** Видаляє Check Call 2 ***
        rowCheckCalls2.$(".glyphicon-trash").shouldBe(visible, enabled).click();

        // Check Call 2 попап діалог
        String popapText2 = switchTo().alert().getText();

        // Check Call 2 попап підтвердження видалення
        assertThat(popapText2).isEqualTo("Are you sure you want to delete this load note?");
        switchTo().alert().accept();

        // Check Call 2 перевірка що видалений
        rowCheckCalls1.shouldNot(exist);

        // *** Видаляє Check Call 2 ***
        rowCheckCalls3.$(".glyphicon-trash").shouldBe(visible, enabled).click();

        // Check Call 3 попап діалог
        String popapText3 = switchTo().alert().getText();

        // Check Call 3 попап підтвердження видалення
        assertThat(popapText3).isEqualTo("Are you sure you want to delete this load note?");
        switchTo().alert().accept();

        // Check Call 3 перевірка що видалений
        rowCheckCalls1.shouldNot(exist);

        // додаткова перевірка що таблиця Check Calls пуста
        $(".table-dispatch-check-calls").shouldHave(text("No results found."));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
