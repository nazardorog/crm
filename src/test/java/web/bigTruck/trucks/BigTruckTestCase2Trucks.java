package web.bigTruck.trucks;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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

public class BigTruckTestCase2Trucks {

    // Click Up:
    // CRM SEMI Truck
    // Trucks
    // 2. Редактирование трака

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void editTruckBigTruck() throws InterruptedException {

        System.out.println("BigTruckTestCase2Truck - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //переходить до списку Truck
        $(".trucks-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".trucks-user").click();
        $("body").click();

        //клік по New Truck
        $("#new_truck").click();

        // *** Вкладка General фрейму Add truck ***

        Random random = new Random();
        String randomNumber = String.format("%3d", random.nextInt(1000));

        // *** Вкладка General фрейму Add truck ***
        String atTruckNumber = "Truck Number auto test 1" + randomNumber;
        String atVinNumber = "VIN12345678999" + randomNumber;
        String atPlateNumber = "Plate auto test 1" + randomNumber;
        String atPlateState = "PL";
        String atModel = "Volvo auto test 1";
        String atColor = "Green auto test 1";
        String atYear = "2025";
        String atMake = "Make truck auto test 1";
        String atZip = "30318";
        String atCity = "Atlanta";
        String atState = "GA";
        String atOwner = "AutoTestOwner1 INC";
        String atNote = "Note truck 1";
        String atStatus = "Available";

        $("#trucks-truck_number").setValue(atTruckNumber);
        $("#trucks-vin_number").setValue(atVinNumber);
        $("#trucks-plate_number").setValue(atPlateNumber);
        $("#trucks-plate_state").setValue(atPlateState);
        $("#trucks-model").setValue(atModel);
        $("#trucks-color").setValue(atColor);
        $("#trucks-year").setValue(atYear);
        $("#trucks-make").setValue(atMake);
        $("#trucks-type_truck").selectOption("Semi truck");

        //календар Date When Will Be There
        $(".kv-datetime-picker").click();
        inputCalendar(0, 0);

        $("#trucks-status").selectOption(atStatus);
        $("#trucks-title_status").selectOption("Clean");
        $("#trucks-cab_type").selectOption("Sleeper");

        //пошук по Zip коду
        $("#trucks-last_zip").setValue(atZip);
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value(atCity));
        $("#trucks-last_state").shouldHave(value(atState));

        //поле Owner
        $("#select2-owner_id-create-container").click();
        $(".select2-search__field").setValue("AutoTestOwner");
        $$("li.select2-results__option")
                .findBy(text(atOwner))
                .click();

        //календар Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        inputCalendarDayOnly(0, 0);

        //календар Annual Vehicle
        $("#trucks-annual_vehicle_expiration-kvdate .kv-date-picker").click();
        inputCalendarDayOnly(0, 0);

        //поле Note
        $("#trucks-note").setValue(atNote);

        // *** Вкладка Documents фрейму Add truck ***
        $("a[href='#documents']").shouldBe(visible).click();
        $(".add-document").shouldBe(visible).click();

        //завантажує файл
        File file = new File("C:/Empire/pdf1.pdf");
        $("#truckdocuments-0-file").uploadFile(file);

        //клік по кнопці Submit фрейму Add truck
        $("#add_truck_send").click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("Truck successfully added"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє створений Truck в списку
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible).setValue(atTruckNumber).pressEnter();

        SelenideElement truckNumber = $$("table.color-table-bigtruck tbody tr")
                .get(0)
                .shouldHave(text(atTruckNumber));

        truckNumber.shouldHave(text(atTruckNumber));
        truckNumber.shouldHave(text(atVinNumber));
        truckNumber.shouldHave(text(atPlateNumber));
        truckNumber.shouldHave(text(atStatus));
        truckNumber.shouldHave(text(atMake));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atYear));

        // *** Редагування Truck вкладка General фрейму Add truck ***
        truckNumber.$(".glyphicon-pencil").click();

        //редагування дані редагування вкладка General
        String atVinNumberEdit = "VIN99345678999" + randomNumber;
        String atPlateNumberEdit = "Plate auto test 2" + randomNumber;
        String atPlateStateEdit = "PL";
        String atModelEdit = "Volvo auto test 2";
        String atColorEdit = "Green auto test 2";
        String atYearEdit = "2022";
        String atMakeEdit = "Make truck auto test 2";
        String atZipEdit = "76827";
        String atCityEdit = "Brookesmith";
        String atStateEdit = "TX";
        String atOwnerEdit = "AutoTestOwner2 INC";
        String atNoteEdit = "Note truck 2";
        String atStatusEdit = "Available On";

        //вводить дані редагування
        $(".bootstrap-dialog-title").shouldHave(text("Update truck"), Duration.ofSeconds(20));
        $("#trucks-vin_number").setValue(atVinNumberEdit);
        $("#trucks-plate_number").setValue(atPlateNumberEdit);
        $("#trucks-plate_state").setValue(atPlateStateEdit);
        $("#trucks-model").setValue(atModelEdit);
        $("#trucks-color").setValue(atColorEdit);
        $("#trucks-year").setValue(atYearEdit);
        $("#trucks-make").setValue(atMakeEdit);

        //редагування календар Date When Will Be There
        $(".kv-datetime-picker").click();
        inputCalendar(1, 0);

        $("#trucks-status").selectOption("Available On");
        $("#trucks-title_status").selectOption("Salvage");
        $("#trucks-cab_type").selectOption("Day cab");

        //редагування пошук по Zip коду
        $("#trucks-last_zip").setValue(atZipEdit);
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value(atCityEdit));
        $("#trucks-last_state").shouldHave(value(atStateEdit));

        //редагування поле Owner
        $("#select2-owner_id-update-container").click();
        $(".select2-search__field").setValue("AutoTestOwner");
        $$("li.select2-results__option")
                .findBy(text(atOwnerEdit))
                .click();

        //редагування календар Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        inputCalendarDayOnly(1, 0);

        //редагування календар Annual Vehicle
        $("#trucks-annual_vehicle_expiration-kvdate .kv-date-picker").click();
        inputCalendarDayOnly(1, 0);

        //редагування поле Note
        $("#trucks-note").setValue(atNoteEdit);

        //редагування Submit фрейм Update truck
        $("#update_truck_send").click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("Truck sucessfully updated"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        // *** Перевіряє відредаговані дані Truck в списку ***
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible).setValue(atTruckNumber).pressEnter();

        SelenideElement rowTruck = $$("table.color-table-bigtruck tbody tr")
                .get(0)
                .shouldHave(text(atTruckNumber));

        rowTruck.shouldHave(text(atTruckNumber));
        rowTruck.shouldHave(text(atVinNumberEdit));
        rowTruck.shouldHave(text(atPlateNumberEdit));
        rowTruck.shouldHave(text(atStatusEdit));
        rowTruck.shouldHave(text(atMakeEdit));
        rowTruck.shouldHave(text(atModelEdit));
        rowTruck.shouldHave(text(atModelEdit));
        rowTruck.shouldHave(text(atYearEdit));

        // *** Перевіряє відредаговані дані Truck фрейм Truck owner ***

        //клік по кнопці око
        rowTruck.$(".glyphicon-eye-open").click();
        $("#view_truck").shouldBe(visible, Duration.ofSeconds(10));

        //перевіряє відредаговані дані Truck в View truck
        $("table#w0").$$("tr").findBy(text("Vin Number"))
                .$$("td").first().shouldHave(text(atVinNumberEdit));

        $("table#w0").$$("tr").findBy(text("Plate Number"))
                .$$("td").first().shouldHave(text(atPlateNumberEdit));

        $("table#w0").$$("tr").findBy(text("Make"))
                .$$("td").first().shouldHave(text(atMakeEdit));

        $("table#w0").$$("tr").findBy(text("Model"))
                .$$("td").first().shouldHave(text(atModelEdit));

        $("table#w0").$$("tr").findBy(text("Type Truck"))
                .$$("td").first().shouldHave(text("Semi truck"));

        $("table#w0").$$("tr").findBy(text("Year"))
                .$$("td").first().shouldHave(text(atYearEdit));

        $("table#w0").$$("tr").findBy(text("Updated By"))
                .$$("td").first().shouldHave(text("Auto 2Test BT"));

        $("table#w0").$$("tr").findBy(text("Note"))
                .$$("td").first().shouldHave(text(atNoteEdit));

        //закриває фрейм View truck
        $("#view_truck button.close").click();
        $("#view_truck").shouldNotBe(visible, Duration.ofSeconds(10));

        System.out.println("BigTruckTestCase2Truck - Test Pass");
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

        // календар що зараз відкритий
        SelenideElement activeCalendar = $$(".datetimepicker").filter(Condition.visible).get(0); // перший видимий

        // Перемикає місяць ТІЛЬКИ в цьому календарі
        if (switchMonth) {
            activeCalendar.$(".datetimepicker-days .next").click();
        }

        // Клікає дату в цьому календарі
        activeCalendar.$$(".datetimepicker-days .day:not(.old):not(.new)")
                .findBy(exactText(String.valueOf(targetDay)))
                .click();

        // Вибираємо час (тільки в активному календарі)
        activeCalendar.$$(".datetimepicker-hours .hour")
                .findBy(exactText(hour + ":00"))
                .click();

        activeCalendar.$$(".datetimepicker-minutes .minute")
                .findBy(exactText(String.format("%d:%02d", hour, minute)))
                .click();
    }

    public void inputCalendarDayOnly(int introductionDay, int numberCalendar){

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

        ElementsCollection dateElement = $$(".datepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).click();
    }
}
