package web.bigTruck;

import com.codeborne.selenide.ElementsCollection;
import org.testng.annotations.Test;
import web.LoginUser2;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase2LoadBoard extends LoginUser2 {

    @Test
    public void editCargoBig () throws InterruptedException {

        //створює новий вантаж
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(1)).click();
//        $("#new_load").click();
//
//        //прибрати віджет чат
////        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
//
//        //поточний час по серверу
//        LocalDateTime now = LocalDateTime.now();
//        int day = now.getDayOfMonth();
//        int hour = now.getHour();
//        int minute = (now.getMinute() / 5) * 5;
//
//        //brocker
//        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
//        $("#select2-broker_search-container").shouldBe(visible).click();
//        $(".select2-search__field").shouldBe(visible).setValue("Auto test broker");
//        $$(".select2-results__options")
//                .findBy(text("Auto test broker"))
//                .click();
//        $("#select2-broker-agent-load-select-container").click();
//        $(".select2-search__field").setValue("Auto test agent");
//        $$(".select2-results__options")
//                .findBy(text("Auto test agent"))
//                .click();
//
//        //input other data
//        Random random = new Random();
//        String reference = String.format("%3d", random.nextInt(10000000));
//        String commodity = String.format("%3d", random.nextInt(10000000));
//        $("#loads-reference").setValue(reference);
//        $("#loads-rate-disp").setValue("100000").pressEnter();
//        $("#loads-commodity_big_trucks_note").setValue(commodity);
//        $("#select2-booked_with-container").shouldHave(text("Auto 2Test"));
//        $("#loads-commodity").setValue("Text Commodity Notes");
//        $$("div#loads-check_full_load label").findBy(text("FTL")).click();
//        $$("#loads-local_type label").findBy(text("Local")).click();
//        $$("#loads-load_type label").findBy(text("Board")).click();
//
//        //load file
//        $(".load_documents_counter-flex").click();
//        $("#select2-loaddocuments-0-type-container").click();
//        $$(".select2-results__option").findBy(text("Rate confirmation")).click();
//
//        File file = new File("C:/Empire/pdf1.pdf");
//        $("#loaddocuments-0-file").uploadFile(file);
//        $("#load_documents_modal_pseudo_submit").click();
//
//        //вкладка Origin & Destination
//        $("#origin-destination-tab").click();
//
//        //Origin Shippers
//        $("#select2-shippers-receiver-origin-container").click();
//        $(".select2-search__field").setValue("Auto test shipper 1");
//        $$("li.select2-results__option")
//                .findBy(text("Auto test shipper 1"))
//                .click();
//
//        //Destination Shippers
//        $("#select2-shippers-receiver-destination-container").click();
//        $(".select2-search__field").setValue("Auto test shipper 2");
//        $$("li.select2-results__option")
//                .findBy(text("Auto test shipper 2"))
//                .click();
//
//        //calendar Origin Shippers Date from
//        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
//        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
//        dateElement.findBy(exactText(String.valueOf(day))).click();
//        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
//        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
//
//        //calendar Origin Shippers Date to
//        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
//        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
//        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
//        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
//
//        //calendar Destination Shippers Date from
//        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
//        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
//        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
//        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();
//
//        //calendar Destination Shippers Date to
//        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
//        dateElement.findBy(exactText(String.valueOf(day + 3))).click();
//        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click();
//        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();
//
//        //pallets Origin Shippers
//        $("#loadspickuplocations-0-pallets").setValue("1");
//        $("#loadspickuplocations-0-weight").setValue("1");
//        $("#loadspickuplocations-0-pcs").setValue("1");
//
//        //pallets Destination Shippers
//        $("#loadsdeliverylocations-0-pallets").setValue("1");
//        $("#loadsdeliverylocations-0-weight").setValue("1");
//        $("#loadsdeliverylocations-0-pcs").setValue("1");
//
//        $("#info-tab").click();
//        $("#loads-load_miles").setValue("200");
//
//        //клік по кнопці "Submit & Dispatch" на фрейм New Load
//        $("#add_load_send_dispatch").click();
//
//        //dispatch board
//        $("#view_load").shouldBe(visible).shouldHave(text("Dispatch #"));
//
        //отримує номер вантажу
        String loadNumber = "30815";
//        String loadNumber = $("#view_load .check_call_pro").getText();
//
//        //клік add Driver
//        $("a[title='Add Driver'] .glyphicon.icon-plus-load").click();
//
//        //вибарає Carrier
//        $("#select2-carrierId-container").click();
//        $$(".select2-results__option").findBy(text("AutoTestOwner5 INC")).click();
//        $("#select2-carrierId-container").shouldHave(text("AutoTestOwner5 INC"));
//
//        //вибарає Truck
//        $("#select2-trucks-template-container").click();
//        $(".select2-search__field").setValue("0305");
//        $$(".select2-results__option").findBy(text("0305 (AutoTestOwner5 INC)")).click();
//        $("#select2-trucks-template-container").shouldHave(text("0305 (AutoTestOwner5 INC)"));
//
//        //вибарає Driver
//        $("#select2-load_driver_id-container").click();
//        $(".select2-search__field").setValue("Auto");
//        $$(".select2-results__option").findBy(text("Auto Test Driver3 Big Truck")).click();
//        $("#select2-load_driver_id-container").shouldHave(text("Auto Test Driver3 Big Truck"));
//
//        //вибарає Team Driver
//        $("#select2-load_team_driver_id-container").click();
//        $(".select2-search__field").setValue("Auto");
//        $$(".select2-results__option").findBy(text("Auto Test Driver4 Big Truck")).click();
//        $("#select2-load_team_driver_id-container").shouldHave(text("Auto Test Driver4 Big Truck"));
//
//        //вибарає Trailer
//        $("#select2-trailer_id-create-container").click();
//        $(".select2-search__field").setValue("Auto");
//        $$(".select2-results__option").findBy(text("AutoTest Trailer")).click();
//        $("#select2-trailer_id-create-container").shouldHave(text("AutoTest Trailer"));
//
//        //вибирає Location From вводить Location To
//        $("#loadexpenses-location").selectOption("Kansas City, MO 64110");
//        $("#loadexpenses-location_to").setValue("New York, NY 10002");
//
//        //вибирає Start Date
//        $(".kv-datetime-picker").click();
//        dateElement.findBy(exactText(String.valueOf(day))).click();
//        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
//        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();
//
//        //перевіряє вибрану дату Start Date
//        LocalDateTime formattedDate = now.withMinute(minute).withSecond(0).withNano(0);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy HH:mm");
//        String formattedDateTime = formattedDate.format(formatter);
//        String selectedDate = $("#loadexpenses-start_date").getValue();
//        Thread.sleep(10000);
//        if (selectedDate.equals(formattedDateTime)) {
//            System.out.println("✅ Дата відображається правильно: " + selectedDate);
//        } else {
//            $("#loadexpenses-start_date").shouldHave(value("3/28/2025 10:35"));
//        }
//
//        //клік по Submit фрейм Add driver
//        $("#update_load_driver_send").click();
//
//        //закриває модальне вікно Dispatch Load
//        $(".load-info-modal-dialog .close").click();
//
//        //клік по лого Empire National
//        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10)).click();
//        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
//        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
//        $("a.view_load").shouldBe(text(loadNumber));
//
        //редагує створений вантаж
        $(".logo-mini-icon").shouldBe(visible).click();
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).pressEnter();

        //редагування вантажу
        $("#main-loads-grid .dropdown-toggle").click();
        $$(".dropdown-menu-right li").findBy(text("Edit Load")).click();

//        editeCargo(loadNumber);

        System.out.println("bigTruckTestCase1LoadBoard - OK");
    }

//    void editeCargo(String loadNumber){
//
//
//
//
//    }
}
