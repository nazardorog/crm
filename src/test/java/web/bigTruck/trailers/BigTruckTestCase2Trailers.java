package web.bigTruck.trailers;

import com.codeborne.selenide.Condition;
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

public class BigTruckTestCase2Trailers {

    // Click Up:
    // CRM SEMI Truck
    // Trailers
    // 1. Создание трейлера Reefer

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void newTrailersReeferBigTruck() throws InterruptedException {

        System.out.println("BigTruckTestCase2Trailers - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //переходить до списку Trailers
        $(".trailers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".trailers-user").click();
        $("body").click();

        //New Trailer
        $("#new_trailer").shouldBe(visible, Duration.ofSeconds(10)).click();

        //фрейм Add trailer
        $("#add_trailer").shouldBe(visible, Duration.ofSeconds(10));

        //дані для створення Trailer
        Random random = new Random();
        int randomNumber = random.nextInt(1000);

        String atTrailerNumber = "Trailer Number auto test 1" + randomNumber;
        String atVinNumber = "VIN12345678999" + randomNumber;;
        String atPlateNumber = "Plate auto test 1" + randomNumber;
        String atPlateNumberState = "PL";
        String atMake = "Make trailer auto test 1";
        String atModel = "Volvo auto test 1";
        String atTypeTrailer = "53' Reefer";
        String atYear = "2025";
        String atOwner = "AutoTestOwner1 INC";
        String atNote = "Note trailer auto test 1";
        String atDescription = "Description auto test 1";
        String atSerialNumber = "SN number 123456789";

        //вводить дані вкладка General
        $("#trailers-trailer_number").setValue(atTrailerNumber);
        $("#trailers-vin_number").setValue(atVinNumber);
        $("#trailers-plate_number").setValue(atPlateNumber);
        $("#trailers-plate_state").setValue(atPlateNumberState);
        $("#trailers-make").setValue(atMake);
        $("#trailers-model").setValue(atModel);
        $("#trailers-type_trailer").selectOption(atTypeTrailer);
        $("#trailers-year").setValue(atYear);

        //календар Inspection Expiration date
        $("#trailers-annual_vehicle_expiration-kvdate .kv-date-picker").click();
        inputCalendarDayOnly(0, 0);

        //календар Inspection Expiration date
        $("#trailers-registration_expiration-kvdate .kv-date-picker").click();
        inputCalendarDayOnly(0, 0);

        //поле Owner
        $("#select2-owner_id-create-container").click();
        $(".select2-search__field").setValue("AutoTestOwner");
        $$("li.select2-results__option")
                .findBy(text(atOwner))
                .click();

        //SN reefer
        $("#trailers-reefer_serial_number").setValue(atSerialNumber);

        //поле Note
        $("#trailers-note").setValue(atNote);

        // *** Вкладка Documents фрейму Add trailers ***
        $("a[href='#documents']").shouldBe(visible).click();

        //завантажує файл
        File file = new File("C:/Empire/pdf1.pdf");
        $("#trailerdocuments-0-file").uploadFile(file);

        //поле Description
        $("#trailerdocuments-0-description").setValue(atDescription);
        $("#trailerdocuments-0-description").shouldHave(value(atDescription), Duration.ofSeconds(10));

        Selenide.sleep(5000);

        //клік по кнопці Submit фрейму Add trailer
        $("#add_trailer_send").shouldBe(clickable).click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(40));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("trailer sucessfully added"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє створений Trailer в списку
        $("input[name='TrailersSearch[trailer_number]']").shouldBe(visible).setValue(atTrailerNumber).pressEnter();

        SelenideElement trailerNumber = $$("table.table-striped tbody tr")
                .get(0)
                .shouldHave(text(atTrailerNumber));

        trailerNumber.shouldHave(text(atTrailerNumber));
        trailerNumber.shouldHave(text(atVinNumber));
        trailerNumber.shouldHave(text(atPlateNumber));
        trailerNumber.shouldHave(text(atMake));
        trailerNumber.shouldHave(text(atModel));
        trailerNumber.shouldHave(text(atTypeTrailer));
        trailerNumber.shouldHave(text(atYear));
        trailerNumber.shouldHave(text(atOwner));

        System.out.println("BigTruckTestCase2Truck - Test Pass");
    }

    public void inputCalendarDayOnly(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        SelenideElement activeCalendar = $$(".datepicker").filter(Condition.visible).get(0);

        //якщо день введення більше ніж кількість днів в місяця, перемикає календар на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        if (switchMonth) {
            activeCalendar.$(".datepicker-days .next").click();
        }

        activeCalendar.$$(".datepicker-days .day:not(.old):not(.new)")
                .findBy(exactText(String.valueOf(targetDay)))
                .click();
    }

}
