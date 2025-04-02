package web.bigTruck;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.Test;
import web.LoginUser2;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase3LoadBoard extends LoginUser2 {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 3. Перевод груза с available load в еn routе

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void availableCargoToEnRout () throws InterruptedException {

//        //створює новий вантаж
//        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30)).click();
//        $("#new_load").click();
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
//        $("#select2-booked_with-container").shouldHave(text("Auto 2Test BT"));
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
//        $("#select2-shippers-receiver-origin-container").shouldBe(enabled).click();
//        $(".select2-search__field").setValue("Auto test shipper 1");
//        $$("li.select2-results__option")
//                .findBy(text("Auto test shipper 1"))
//                .click();
//
//        //Destination Shippers
//        $("#select2-shippers-receiver-destination-container").shouldBe(enabled).click();
//        $(".select2-search__field").setValue("Auto test shipper 2");
//        $$("li.select2-results__option")
//                .findBy(text("Auto test shipper 2"))
//                .click();
//
//        //calendar Origin Shippers Date from
//        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
//        inputCalendar(1, 0);
//
//        //calendar Origin Shippers Date to
//        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
//        inputCalendar(2, 1);
//
//        //calendar Destination Shippers Date from
//        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
//        inputCalendar(3, 2);
//
//        //calendar Destination Shippers Date to
//        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
//        inputCalendar(4, 3);
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
//        //зберігає номер вантажу на фрейм Dispatch board
//        $("#add_load_send_dispatch").click();
//        $("#view_load").shouldBe(visible).shouldHave(text("Dispatch #"));
//
//        //отримує номер вантажу
//        String loadNumber = $("#view_load .check_call_pro").getText();
//
//        //закриває модальне вікно Dispatch Load
//        $(".load-info-modal-dialog .close").shouldBe(enabled).click();

        String loadNumber = "31038";

        //перевіряє що вантаж створено в Load bord вводить номер вантажу і перевіряє що він є в таб частині
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Available Loads:")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(enabled).click();
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).pressEnter();
        $("td a.view_load").shouldHave(text(loadNumber));

        //клік на око, редагування вантажу Dispatch load
        $(".icon-glyphicon-eye-open").click();




        //клік по кнопці "Submit" на фрейм New Load
        $("#add_load_send").click();
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

}
