package web.bigTruck.loadBoard;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase2LoadBoard {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 2. Редактирование Груза

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void editCargoBigTruck () throws InterruptedException {

        System.out.println("BigTruckTestCase2LoadBoard - Start");

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

        $("#info-tab").click();

        //клік по кнопці "Submit & Dispatch" на фрейм New Load
        $("#add_load_send_dispatch").click();

        //dispatch board
        $("#view_load").shouldBe(visible).shouldHave(text("Dispatch #"));

        //отримує номер вантажу
        String loadNumber = $("#view_load .check_call_pro").getText();
        System.out.println("BigTruckTestCase2LoadBoard. Номер вантажу:" + loadNumber);

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

        //перевіряє вибрану дату Start Date
        LocalDateTime choseDate = now.withMinute(minute).withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy HH:mm");
        String formattedDateTime = choseDate.format(formatter);
        String displayedDate = $("#loadexpenses-start_date").getValue();
        Thread.sleep(10000);

        if (displayedDate.equals(formattedDateTime)) {
            System.out.println("✅ Дата відображається правильно: " + choseDate);
        } else {
            System.out.println("✅ Дата відображається не правильно: " + choseDate);
        }

        //клік по Submit фрейм Add driver
        $("#update_load_driver_send").click();
        $("#add_driver").shouldNotBe(visible, Duration.ofSeconds(20));

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //Load Board знаходить створений вантаж на вкладці Loads en Route
        //String loadNumber = "30957";
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();

        //перевіряє дані створеного вантажу
        $("td a.view_load").shouldHave(text(loadNumber));
        $("td a.view_truck").shouldHave(text("0305"));
        $("td .text-aqua").shouldHave(text("AutoTest Trailer1"));
        $(".bt-col-driver-carrier .drivers-wrap").shouldHave(exactText("Auto Test Driver3 Big Truck"));
        $(".bt-col-driver-carrier .team-driver-wrap").shouldHave(exactText("Auto Test Driver4 Big Truck"));

        $("td .view_broker").shouldHave(text("Auto test broker"));
        $(".loads-locations .view_pick_up_location").shouldHave(text("Kansas City, MO"));
        $(".col-origin .big-truck-none-bold").shouldHave(text("Wt 1 Plt 1 Pcs 1"));
        $(".loads-locations .view_delivery_location").shouldHave(text("New York, NY"));
        $(".col-destination .big-truck-none-bold").shouldHave(text("Wt 1 Plt 1 Pcs 1"));

        //перевіряє дату Origin Shippers Date from, формат мм/дд
        LocalDateTime futureDate = now.plusDays(1);
        String formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(".col-origin .loads-locations-time-frame .pull-right ").get(0).shouldHave(text(formattedDate));

        //перевіряє дату Origin Shippers Date to, формат мм/дд
        futureDate = now.plusDays(2);
        formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(".col-origin .loads-locations-time-frame .pull-right ").get(1).shouldHave(text(formattedDate));

        //перевіряє дату Destination Shippers Date from, формат мм/дд
        futureDate = now.plusDays(3);
        formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(".col-destination .loads-locations-time-frame .pull-right ").get(0).shouldHave(text(formattedDate));

        //перевіряє дату Destination Shippers Date to, формат мм/дд
        futureDate = now.plusDays(4);
        formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(".col-destination .loads-locations-time-frame .pull-right ").get(1).shouldHave(text(formattedDate));

        //клік редагування вантажу
        $("#main-loads-grid .dropdown-toggle").shouldBe(visible,enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Edit Load")).shouldBe(enabled, Duration.ofSeconds(10)).click();

        //вводить нові дані редагування
        //brocker
        $("#update_load").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").shouldBe(visible).setValue("Auto test broker10");
        $$(".select2-results__options")
                .findBy(text("Auto test broker10"))
                .click();

        //agent brocker
        $("#select2-broker-agent-load-select-container")
                .shouldNotHave(text("Auto test agent"), Duration.ofSeconds(5));

        $("#select2-broker-agent-load-select-container").shouldBe(visible).click();
        $(".select2-search__field").shouldBe(visible).setValue("Auto test agent39");
        $$(".select2-results__options")
                .findBy(text("Auto test agent39"))
                .shouldBe(visible, Duration.ofSeconds(5))
                .click();

        //input other data
        $("#loads-rate-disp").setValue("175000").pressEnter();
        $("#select2-booked_with-container").shouldHave(text("Auto 2Test BT"));
        $$("div#loads-check_full_load label").findBy(text("PTL")).click();
        $$("#loads-local_type label").findBy(text("OTR")).click();
        $$("#loads-load_type label").findBy(text("Warm")).click();

        $("#select2-booked_with-container").shouldBe(enabled).click();
        $$(".select2-results__options")
                .findBy(text("Auto 3Test BT"))
                .click();

        $("#update_load_send").shouldBe(enabled).click();

        //клік редагування вантажу
        $("#main-loads-grid .dropdown-toggle").shouldBe(enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Edit Load")).shouldBe(enabled).click();

        $("#loads-rate-disp").shouldBe(visible).shouldHave(value("$ 1,750.00"));
        $("#select2-booked_with-container").shouldHave(text("Auto 3Test BT"));
        $$("#loads-check_full_load label")
                .findBy(text("PTL"))
                .$("input")
                .shouldBe(checked);

        $$("#loads-local_type label")
                .findBy(text("OTR"))
                .$("input")
                .shouldBe(checked);

        $$("#loads-load_type label")
                .findBy(text("Warm"))
                .$("input")
                .shouldBe(checked);

        System.out.println("bigTruckTestCase2LoadBoard - Test Pass");
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
