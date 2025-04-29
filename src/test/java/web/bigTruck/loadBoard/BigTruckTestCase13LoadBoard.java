package web.bigTruck.loadBoard;

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
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase13LoadBoard {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 13. Чек Колы - Редактирование

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;
    String loadNumber;
    String agent = "Auto test agent";

    @Test
    public void checkCallEdit () throws InterruptedException {

        System.out.println("BigTruckTestCase13LoadBoard - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //створює новий вантаж
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

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
        inputCalendar(1, 0);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(2, 1);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(3, 2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(4, 3);

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //відкриває фрейм Dispatch board
        $("#add_load_send_dispatch").click();
        $("#view_load").shouldBe(visible).shouldHave(text("Dispatch #"));

        //отримує номер вантажу
        loadNumber = $("#view_load .check_call_pro").getText();
        System.out.println("BigTruckTestCase3LoadBoard. Номер вантажу:" + loadNumber);

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
        inputCalendar(0, 0);

        //клік по Submit фрейм Add driver
        $("#update_load_driver_send").click();
        $("#add_driver").shouldNotBe(visible, Duration.ofSeconds(20));

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //перевіряє що вантаж відображається на Loads en Route
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
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

        // new Call 1 поле Location
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
        inputCalendar(0, 0);

        // new Call 3 клік Submit фрейм Check Call
        $("#check_call_load_send")
                .click();

        // *** Дані для редагування CheckCalls
        // Дані edit Check Call 1
        String editNoteCheckCalls1 = "Edit Note Check cool 1";
        String editTapeCheckCalls1 = "User";
        String editZipCheckCalls1 = "78702";
        String editLocationCheckCalls1 = "Austin, TX";
        String editUserCheckCalls1 = "Auto 2Test BT";

        // Дані edit Check Call 2
        String editNoteCheckCalls2 = "Edit Note Check cool 2";
        String editTapeCheckCalls2 = "Alert";
        String editZipCheckCalls2 = "61611";
        String editLocationCheckCalls2 = "East Peoria, IL";
        String editUserCheckCalls2 = "Auto 2Test BT";

        // Дані edit Check Call 3
        String editNoteCheckCalls3 = "Edit Note Check cool 3";
        String editTapeCheckCalls3 = "LFB";
        String editZipCheckCalls3 = "76502";
        String editLocationCheckCalls3 = "Temple, TX";
        String editUserCheckCalls3 = "Auto 2Test BT";

        // *** Check Call 1 редагування ***
        // Check Call 1 перевіряє дані до редагування фрейм Dispatch
        SelenideElement rowCheckCalls1 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(noteCheckCalls1));
        rowCheckCalls1.$("td:nth-of-type(2)").shouldHave(text(tapeCheckCalls1));
        rowCheckCalls1.$(".check_call_user").shouldHave(text(userCheckCalls1));
        rowCheckCalls1.$(".check_call_location").shouldHave(text(locationCheckCalls1));
        rowCheckCalls1.$(".check_call_note").shouldHave(text(noteCheckCalls1));

        // Check Call 1 клік по олівець
        rowCheckCalls1.$(".update_load_note .glyphicon-pencil").shouldBe(visible, enabled).click();
        $("#update_load_note").shouldBe(visible);

        // Check Call 1 перевіряє дані до редагування фрейм Update Load Note
        $("#loadnotes-location").shouldHave(value(locationCheckCalls1 + " " + zipCheckCalls1));
        $("#loadnotes-note").shouldHave(text(noteCheckCalls1));

        // Check Call 1 редагує дані
        $("#loadnotes-location").setValue(editZipCheckCalls1);
        $$("#autocomplete-results-loadnotes-location li").findBy(text(editLocationCheckCalls1 + " " + editZipCheckCalls1)).shouldBe(visible).click();
        $("#loadnotes-note").setValue(editNoteCheckCalls1).shouldBe(visible);
        $("#update_load_note_send").click();

        // Check Call 1 перевіряє дані після редагування фрайм Dispatch
        SelenideElement editRowCheckCalls1 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(editNoteCheckCalls1));
        editRowCheckCalls1.$("td:nth-of-type(2)").shouldHave(text(editTapeCheckCalls1));
        editRowCheckCalls1.$(".check_call_user").shouldHave(text(editUserCheckCalls1));
        editRowCheckCalls1.$(".check_call_location").shouldHave(text(editLocationCheckCalls1));
        editRowCheckCalls1.$(".check_call_note").shouldHave(text(editNoteCheckCalls1));

        // *** Check Call 2 редагування ***
        // Check Call 2 перевіряє дані до редагування фрейм Dispatch
        SelenideElement rowCheckCalls2 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(noteCheckCalls2));
        rowCheckCalls2.$("td:nth-of-type(2)").shouldHave(text(tapeCheckCalls2));
        rowCheckCalls2.$(".check_call_user").shouldHave(text(userCheckCalls2));
        rowCheckCalls2.$(".check_call_location").shouldHave(text(locationCheckCalls2));
        rowCheckCalls2.$(".check_call_note").shouldHave(text(noteCheckCalls2));

        // Check Call 2 клік по олівець
        rowCheckCalls2.$(".update_load_note .glyphicon-pencil").shouldBe(visible, enabled).click();
        $("#update_load_note").shouldBe(visible);

        // Check Call 2 перевіряє дані до редагування фрейм Update Load Note
        $("#loadnotes-location").shouldHave(value(locationCheckCalls2 + " " + zipCheckCalls2));
        $("#loadnotes-note").shouldHave(text(noteCheckCalls2));

        // Check Call 2 редагує дані
        $("#loadnotes-location").setValue(editZipCheckCalls2);
        $$("#autocomplete-results-loadnotes-location li").findBy(text(editLocationCheckCalls2 + " " + editZipCheckCalls2)).shouldBe(visible).click();
        $("#loadnotes-note").setValue(editNoteCheckCalls2).shouldBe(visible);
        $("#update_load_note_send").click();

        // Check Call 2 перевіряє дані після редагування фрайм Dispatch
        SelenideElement editRowCheckCalls2 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(editNoteCheckCalls2));
        editRowCheckCalls2.$("td:nth-of-type(2)").shouldHave(text(editTapeCheckCalls2));
        editRowCheckCalls2.$(".check_call_user").shouldHave(text(editUserCheckCalls2));
        editRowCheckCalls2.$(".check_call_location").shouldHave(text(editLocationCheckCalls2));
        editRowCheckCalls2.$(".check_call_note").shouldHave(text(editNoteCheckCalls2));

        // *** Check Call 3 редагування ***
        // Check Call 3 перевіряє дані до редагування фрейм Dispatch
        SelenideElement rowCheckCalls3 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(noteCheckCalls3));
        rowCheckCalls3.$("td:nth-of-type(2)").shouldHave(text(tapeCheckCalls3));
        rowCheckCalls3.$(".check_call_user").shouldHave(text(userCheckCalls3));
        rowCheckCalls3.$(".check_call_location").shouldHave(text(locationCheckCalls3));
        rowCheckCalls3.$(".check_call_note").shouldHave(text(noteCheckCalls3));

        // Check Call 3 клік по олівець
        rowCheckCalls3.$(".update_load_note .glyphicon-pencil").shouldBe(visible, enabled).click();
        $("#update_load_note").shouldBe(visible);

        // Check Call 3 перевіряє дані до редагування фрейм Update Load Note
        $("#loadnotes-location").shouldHave(value(locationCheckCalls3 + " " + zipCheckCalls3));
        $("#loadnotes-note").shouldHave(text(noteCheckCalls3));

        // Check Call 3 редагує дані
        $("#loadnotes-location").setValue(editZipCheckCalls3);
        $$("#autocomplete-results-loadnotes-location li").findBy(text(editLocationCheckCalls3 + " " + editZipCheckCalls3)).shouldBe(visible).click();
        $("#loadnotes-note").setValue(editNoteCheckCalls3).shouldBe(visible);
        $("#update_load_note_send").click();

        // Check Call 3 перевіряє дані після редагування фрайм Dispatch
        SelenideElement editRowCheckCalls3 = $$("table.table-dispatch-check-calls tbody tr").findBy(text(editNoteCheckCalls3));
        editRowCheckCalls3.$("td:nth-of-type(2)").shouldHave(text(editTapeCheckCalls3));
        editRowCheckCalls3.$(".check_call_user").shouldHave(text(editUserCheckCalls3));
        editRowCheckCalls3.$(".check_call_location").shouldHave(text(editLocationCheckCalls3));
        editRowCheckCalls3.$(".check_call_note").shouldHave(text(editNoteCheckCalls3));


        System.out.println("bigTruckTestCase13LoadBoard - Test Pass");
    }

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календар на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        if (switchMonth) {
            Selenide.executeJavaScript("arguments[0].click();", $$(".datetimepicker-days .next").get(numberCalendar));
        }

        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).click();

        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        System.out.println("Tear down - close WebDriver");
        web.config.CloseWebDriver.tearDown();
    }
}
