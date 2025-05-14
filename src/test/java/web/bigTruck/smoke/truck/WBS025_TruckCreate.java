package web.bigTruck.smoke.truck;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static utilsWeb.configWeb.GlobalGenerateName.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS025_TruckCreate {

    // Click Up:
    // CRM SEMI Truck
    // Trucks
    // 1. Создание New truck

    String globalNameLettersDigits = globalNameLettersDigits();
    String globalName = globalName();

    @Test
    public void create() throws InterruptedException {

        // Login
        GlobalConfig.OPTION_LOGIN = "big";
        WebDriverConfig.setup();
        LoginHelper.login();

        // Переходить до списку Truck
        $(".trucks-user").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".trucks-user").click();
        $("body").click();

        // Клік New Truck
        $("#new_truck").click();

        // Дані для створення Truck
        String fileName = "1pdf.pdf";
        String atTruckNumber = globalName + "Truck Number 1";
        String atVinNumber = globalNameLettersDigits + "123456789";
        String atPlateNumber = globalName + "Plate Number 1";
        String atPlateState = "PL";
        String atModel = globalName + "Volvo 1";
        String atColor = globalName + "Green 1";
        String atYear = "2025";
        String atMake = globalName + "Make 1";
        String atZip = "30318";
        String atCity = "Atlanta";
        String atState = "GA";
        String atOwner = "AutoTestOwner1 INC";
        String atNote = "Note truck 1";
        String atStatus = "Available";

        // Вводить дані вкладка General
        $("#trucks-truck_number").setValue(atTruckNumber);
        $("#trucks-vin_number").setValue(atVinNumber);
        $("#trucks-plate_number").setValue(atPlateNumber);
        $("#trucks-plate_state").setValue(atPlateState);
        $("#trucks-model").setValue(atModel);
        $("#trucks-color").setValue(atColor);
        $("#trucks-year").setValue(atYear);
        $("#trucks-make").setValue(atMake);
        $("#trucks-type_truck").selectOption("Semi truck");

        // Календар Date When Will Be There
        $(".kv-datetime-picker").click();
        Calendar.setDateTime(0);

        $("#trucks-status").selectOption(atStatus);
        $("#trucks-title_status").selectOption("Salvage");
        $("#trucks-cab_type").selectOption("Sleeper");

        // Пошук по Zip коду
        $("#trucks-last_zip").setValue(atZip);
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value(atCity));
        $("#trucks-last_state").shouldHave(value(atState));

        // Поле Owner
        $("#select2-owner_id-create-container").click();
        $(".select2-search__field").setValue("AutoTestOwner");
        $$("li.select2-results__option")
                .findBy(text(atOwner))
                .click();

        // Календар Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // Календар Annual Vehicle
        $("#trucks-annual_vehicle_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        // Поле Note
        $("#trucks-note").setValue(atNote);

        // Перехід на вкладку "Documents"
        $("a[href='#documents']").shouldBe(visible).click();
        $(".add-document").shouldBe(visible).click();

        // Завантажує файл
        File file = new File(downloadsFolder + fileName);
        $("#truckdocuments-0-file").uploadFile(file);

        // Клік по кнопці Submit фрейму Add truck
        $("#add_truck_send").click();

        // Тост вспливайка
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Truck successfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє створений Truck в списку
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible).setValue(atTruckNumber).pressEnter();

        SelenideElement truckNumber = $$("table.color-table-bigtruck tbody tr")
                .get(0)
                .shouldHave(text(atTruckNumber));

        truckNumber.shouldHave(text(atTruckNumber));
        truckNumber.shouldHave(text(atVinNumber));
        truckNumber.shouldHave(text(atPlateNumber));
        truckNumber.shouldHave(text(atMake));
        truckNumber.shouldHave(text(atState));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atYear));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
