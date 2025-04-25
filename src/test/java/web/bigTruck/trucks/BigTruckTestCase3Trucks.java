package web.bigTruck.trucks;

import com.codeborne.selenide.Condition;
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
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;

public class BigTruckTestCase3Trucks {

    // Click Up:
    // CRM SEMI Truck
    // Trucks
    // 3. Удаление трака

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void deleteTruckBigTruck() throws InterruptedException {

        System.out.println("BigTruckTestCase1Truck - Start");

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
        int randomNumber = random.nextInt(1000);

        // *** Вкладка General фрейму Add owner ***
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

        $("#trucks-status").selectOption("Available");
        $("#trucks-title_status").selectOption("Salvage");
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
        $("#trucks-note").setValue("Note truck");

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

//        String atTruckNumber = "Truck Number auto test 1456";

        //перевіряє створений Truck в списку
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible).setValue(atTruckNumber).pressEnter();

        SelenideElement truckNumber = $$("table.color-table-bigtruck tbody tr")
                .get(0)
                .shouldHave(text(atTruckNumber));

        truckNumber.shouldHave(text(atTruckNumber));
        truckNumber.shouldHave(text(atVinNumber));
        truckNumber.shouldHave(text(atPlateNumber));
        truckNumber.shouldHave(text(atMake));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atYear));

        //видяляє Truck
        truckNumber.$("a[title='Delete']").click();

        //popap підтвердження видалення
        String popapText = switchTo().alert().getText();
        System.out.println("Alert says: " + popapText);

        assertThat(popapText).isEqualTo("Are you sure you want to delete this item?");
        switchTo().alert().accept();

        //перевіряє видалення Truck
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible, Duration.ofSeconds(10)).setValue(atTruckNumber).pressEnter();
        $("#truckssearch-active").selectOption("All");
        $(".empty").shouldBe(text("No results found."));

        System.out.println("BigTruckTestCase3Truck - Test Pass");
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

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        System.out.println("Tear down - close WebDriver");
        web.config.CloseWebDriver.tearDown();
    }
}
