package web.bigTruck.smoke.loadBoard;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS009_LoadDriverAddDell {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 9. Dispatch/Add driver

    @Test
    public void driverAddDell () {

        // Login
        GlobalConfig.OPTION_LOGIN = "big";
        WebDriverConfig.setup();
        LoginHelper.login();

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

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //перевіряє що вантаж відображається на Loads en Route
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));

        //перевіряє дані водія перед редагуванням фрейм Load Board
        $("td a.view_truck").shouldHave(text("0305"));
        $("td .text-aqua").shouldHave(text("AutoTest Trailer1"));
        $(".bt-col-driver-carrier .drivers-wrap").shouldHave(exactText("Auto Test Driver3 Big Truck"));
        $(".bt-col-driver-carrier .team-driver-wrap").shouldHave(exactText("Auto Test Driver4 Big Truck"));

        //відкриває Dispatch
        $("#main-loads-grid button.view_load").click();

        //перевіряє дані dispatch що були при створенні вантажу
        $$("#loadDriversContent .view_driver").get(0).shouldHave(text("Auto Test Driver3 Big Truck"));
        $$("#loadDriversContent .view_driver").get(1).shouldHave(text("Auto Test Driver4 Big Truck"));
        $("#loadDriversContent .text-muted").shouldHave(text("AutoTestOwner1 INC"));
        $("#loadDriversContent span.text-purple").shouldHave(text("0305"));
        $("#loadDriversContent span.text-aqua").shouldHave(text("AutoTest Trailer1"));

        //***Додає другого водія до вантажу***
        $(".dispatch-head-drivers span.icon-plus-load").click();

        //вибирає Carrier
        $("#select2-carrierId-container").click();
        $$(".select2-results__option").findBy(text("AutoTestOwner2 INC")).click();
        $("#select2-carrierId-container").shouldHave(text("AutoTestOwner2 INC"));

        //вибирає Truck
        $("#select2-trucks-template-container").click();
        $(".select2-search__field").setValue("0306");
        $$(".select2-results__option").findBy(text("0306 (AutoTestOwner2 INC)")).click();
        $("#select2-trucks-template-container").shouldHave(text("0306 (AutoTestOwner2 INC)"));

        //вибирає Driver
        $("#select2-load_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver5 Big Truck")).click();
        $("#select2-load_driver_id-container").shouldHave(text("Auto Test Driver5 Big Truck"));

        //вибирає Team Driver
        $("#select2-load_team_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver6 Big Truck")).click();
        $("#select2-load_team_driver_id-container").shouldHave(text("Auto Test Driver6 Big Truck"));

        //вибирає Trailer
        $("#select2-trailer_id-create-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("AutoTest Trailer2")).click();
        $("#select2-trailer_id-create-container").shouldHave(text("AutoTest Trailer2"));

        //вводить Location To
        $("#loadexpenses-location_to").setValue("edit New York, NY 10002");

        //вибирає Start Date
        $(".kv-datetime-picker").click();
        Calendar.setDateTime(1);

        //клік по Submit фрейм Add driver
        $("#update_load_driver_send").click();

        //селектори водія та доданого водія на фрейм dispatch
        ElementsCollection drivers = $$("tr td a.view_driver");
        ElementsCollection owner = $$("tr td a.text-muted");
        ElementsCollection truck = $$("tr td span.text-purple");
        ElementsCollection trailer = $$("tr td span.text-aqua");

        //перевіряє першого водія фрейм dispatch
        drivers.get(0).shouldHave(text("Auto Test Driver3 Big Truck"));
        drivers.get(1).shouldHave(text("Auto Test Driver4 Big Truck"));
        owner.get(0).shouldHave(text("AutoTestOwner1 INC"));
        truck.get(0).shouldHave(text("0305"));
        trailer.get(0).shouldHave(text("AutoTest Trailer1"));

        //перевіряє другого водія фрейм dispatch
        drivers.get(2).shouldHave(text("Auto Test Driver5 Big Truck"));
        drivers.get(3).shouldHave(text("Auto Test Driver6 Big Truck"));
        owner.get(1).shouldHave(text("AutoTestOwner2 INC"));
        truck.get(1).shouldHave(text("0306"));
        trailer.get(1).shouldHave(text("AutoTest Trailer2"));

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //перевіряє дані водія після редагування Load Board вкладка Loads en Route
        //перевіряє першого водія
        $(".loads-driver-height-block.level-0 a.view_truck").shouldHave(text("0305"));
        $(".loads-driver-height-block.level-0 span.text-aqua").shouldHave(text("AutoTest Trailer1"));
        $$(".loads-driver-height-block.level-0 a.view_driver").get(0).shouldHave(text("Auto Test Driver3 Big Truck"));
        $$(".loads-driver-height-block.level-0 a.view_driver").get(1).shouldHave(text("Auto Test Driver4 Big Truck"));

        //перевіряє другого водія
        $(".loads-driver-height-block.level-1 a.view_truck").shouldHave(text("0306"));
        $(".loads-driver-height-block.level-1 span.text-aqua").shouldHave(text("AutoTest Trailer2"));
        $$(".loads-driver-height-block.level-1 a.view_driver").get(0).shouldHave(text("Auto Test Driver5 Big Truck"));
        $$(".loads-driver-height-block.level-1 a.view_driver").get(1).shouldHave(text("Auto Test Driver6 Big Truck"));

        //відкриваємо Dispatch
        $("#main-loads-grid button.view_load").click();

        //перевіряє другого водія фрейм dispatch
        truck.get(1).shouldHave(text("0306"));

        //видаляє другого водія shouldNotHave
        $("#loadDriversContent span.glyphicon-minus").click();

        //перевіряє що другий водій видалений фрейм dispatch
        truck.get(1).shouldNotHave(visible);

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //перевіряє що другий водій видалений та перший відображається Load Board вкладка Loads en Route
        //перевіряє першого водія
        $(".loads-driver-height-block.level-0 a.view_truck").shouldHave(text("0305"));
        $(".loads-driver-height-block.level-0 span.text-aqua").shouldHave(text("AutoTest Trailer1"));
        $$(".loads-driver-height-block.level-0 a.view_driver").get(0).shouldHave(text("Auto Test Driver3 Big Truck"));
        $$(".loads-driver-height-block.level-0 a.view_driver").get(1).shouldHave(text("Auto Test Driver4 Big Truck"));

        //перевіряє що другий водій не відображається
        $(".loads-driver-height-block.level-1 a.view_truck").shouldNotHave(visible);
        $(".loads-driver-height-block.level-1 span.text-aqua").shouldNotHave(visible);
        $(".loads-driver-height-block.level-1 a.view_driver").shouldNotHave(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
