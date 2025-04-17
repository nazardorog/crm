package web.bigTruck;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


import java.io.File;
import java.time.*;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase12LoadBoard {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 12. Чек Колы - Создание

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;
    String loadNumber;
    String agent = "Auto test agent";

    @Test
    public void checkCallAdd () throws InterruptedException {

        System.out.println("BigTruckTestCase12LoadBoard - Start");

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

        File file = new File("C:/Empire/pdf1.pdf");
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

//        loadNumber = "31487";

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

    // *** Додає Check Call 1 ***
        $(".dispatch-head-drivers a.check_call_load")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        $("#add_check_call_load").shouldBe(visible);

        // Поле Location
        $("#loadnotes-location").setValue("7700");
        $$("#autocomplete-results-loadnotes-location li")
                .findBy(text("Houston, TX 77001"))
                .shouldBe(visible)
                .click();

        // Поле Note
        $("#loadnotes-note").setValue("Note Check cool 1").shouldBe(visible);

        // Клік по Submit фрейм Check Call
        $("#check_call_load_send").click();

        //Перевіряє додавання Check Call 1 на Dispatch
        String noteCheckCalls1 = "Note Check cool 1";

        SelenideElement rowCheckCalls1 = $$("table.table-dispatch-check-calls tbody tr")
                .findBy(text(noteCheckCalls1));

        rowCheckCalls1.$("td:nth-of-type(2)").shouldHave(text("User"));
        rowCheckCalls1.$(".check_call_user").shouldHave(text("Auto 2Test BT"));
        rowCheckCalls1.$(".check_call_location").shouldHave(text("Houston, TX"));
        rowCheckCalls1.$(".check_call_note").shouldHave(text("Note Check cool 1"));

    // *** Додає Check Call 2 ***
        $(".dispatch-head-drivers a.check_call_load")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        $("#add_check_call_load").shouldBe(visible);

        // Поле Location
        $("#loadnotes-location").setValue("75216");
        $$("#autocomplete-results-loadnotes-location li")
                .findBy(text("Dallas, TX 75216"))
                .shouldBe(visible)
                .click();

        // Поле Note
        $("#loadnotes-note").setValue("Note Check cool 2").shouldBe(visible);

        // Чек бокс Alert
        $("#loadnotes-alert").click();

        // Клік по Submit фрейм Check Call
        $("#check_call_load_send").click();

        // Перевіряє додавання Check Call 2 на Dispatch
        String noteCheckCalls2 = "Note Check cool 2";

        SelenideElement rowCheckCalls2 = $$("table.table-dispatch-check-calls tbody tr")
                .findBy(text(noteCheckCalls2));

        rowCheckCalls2.$("td:nth-of-type(2)").shouldHave(text("Alert"));
        rowCheckCalls2.$(".check_call_user").shouldHave(text("Auto 2Test BT"));
        rowCheckCalls2.$(".check_call_location").shouldHave(text("Dallas, TX"));
        rowCheckCalls2.$(".check_call_note .alert-note").shouldHave(text("Note Check cool 2"));
        rowCheckCalls2.$(".check_call_note input.alert-solved").shouldBe(visible);
        rowCheckCalls2.$$(".check_call_note span.text-red").findBy(text(" : solve alert")).shouldBe(visible);

// *** Додає Check Call 3 ***
        String locationLfb = "78213";

        $(".dispatch-head-drivers a.check_call_load")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();
        $("#add_check_call_load").shouldBe(visible);

        // Поле Location
        $("#loadnotes-location").setValue(locationLfb);
        $$("#autocomplete-results-loadnotes-location li")
                .findBy(text("San Antonio, TX 78213"))
                .shouldBe(visible)
                .click();

        // Поле Note
        $("#loadnotes-note").setValue("Note Check cool 3").shouldBe(visible);

        // Чек бокс LFB
        $("#loadnotes-location_for_broker").click();
        $(".preview-lfb__head").shouldBe(visible).shouldHave(text("This message will be received by the broker"));
        $$(".preview-lfb__content").findBy(text("Hello " + agent));
        $$(".preview-lfb__content").findBy(text("The driver's current location is "));
        $$(".preview-lfb__content").findBy(text(locationLfb));
        $$(".preview-lfb__content").findBy(text("All good for delivery on "));
        $$(".preview-lfb__content").findBy(text("We will keep you posted"));
        $$(".preview-lfb__content").findBy(text("Thank you."));

        // Calendar ETA
        $("#add_eta-datetime .kv-datetime-picker").click();
        inputCalendar(0, 0);

        // Клік по Submit фрейм Check Call
        $("#check_call_load_send").click();

        // Перевіряє додавання Check Call 3 на Dispatch
        String noteCheckCalls3 = "Note Check cool 3";
        SelenideElement rowCheckCalls3 = $$("table.table-dispatch-check-calls tbody tr")
                .findBy(text(noteCheckCalls3));
        rowCheckCalls3.$("td:nth-of-type(2)").shouldHave(text("LFB"));
        rowCheckCalls3.$(".check_call_user").shouldHave(text("Auto 2Test BT"));
        rowCheckCalls3.$(".check_call_location").shouldHave(text("San Antonio, TX"));
        rowCheckCalls3.$(".lfb-timer").shouldBe(visible);
        rowCheckCalls3.$(".check_call_note").shouldHave(text("Note Check cool 3"));
        rowCheckCalls3.$(".check_call_note").shouldHave(text("Available time to cancel "));

        // Закриває модальне вікно Dispatch Load
        $(".load-info-modal-dialog .close").shouldBe(visible, Duration.ofSeconds(30)).click();

        //перевіряє що вантаж відображається на Loads en Route
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));

        // Перевіряє Check Call на Load Board вкладка Loads en Route
        $(".col-style-checkcall .pull-left").shouldHave(text("Dallas, TX"));
        $(".col-style-checkcall .small-txt").shouldHave(text("Note Check cool 2"));


        System.out.println("bigTruckTestCase12LoadBoard - Test Pass");
    }

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календарь на наступний місяць
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
