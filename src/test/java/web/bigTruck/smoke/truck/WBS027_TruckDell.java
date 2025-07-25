package web.bigTruck.smoke.truck;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
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

public class WBS027_TruckDell {

    // Click Up:
    // CRM SEMI Truck
    // Trucks
    // 3. Удаление трака

    String globalNumberSeventeen = "1234567890" + GlobalGenerateName.globalNumberSeven();

    @Test
    public void dell() {

        // Login
        GlobalLogin.login("bt_disp1");

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
        final String atVinNumber = globalNumberSeventeen;
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
        Calendar.setDateTime(0);

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
        Calendar.setDate(0);

        //календар Annual Vehicle
        $("#trucks-annual_vehicle_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

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

        //видаляє Truck
        truckNumber.$("a[title='Delete']").click();

        //popap підтвердження видалення
        String popapText = switchTo().alert().getText();

        assertThat(popapText).isEqualTo("Are you sure you want to delete this item?");
        switchTo().alert().accept();

        //перевіряє видалення Truck
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible, Duration.ofSeconds(10)).setValue(atTruckNumber).pressEnter();
        $("#truckssearch-active").selectOption("All");
        $(".empty").shouldBe(text("No results found."));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
