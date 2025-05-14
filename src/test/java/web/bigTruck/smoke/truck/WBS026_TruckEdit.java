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
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalGenerateName.globalName;
import static utilsWeb.configWeb.GlobalGenerateName.globalNameLettersDigits;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS026_TruckEdit {

    // Click Up:
    // CRM SEMI Truck
    // Trucks
    // 2. Редактирование трака

    String globalNameLettersDigits = globalNameLettersDigits();
    String globalName = globalName();

    @Test
    public void edit() throws InterruptedException {

        // Login
        GlobalConfig.OPTION_LOGIN = "big";
        WebDriverConfig.setup();
        LoginHelper.login();

        // Переходить до списку Truck
        $(".trucks-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".trucks-user").click();
        $("body").click();

        // Переходить до списку Truck
        $(".trucks-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
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
        $("#trucks-title_status").selectOption("Clean");
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

        // *** Вкладка Documents фрейму Add truck ***
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
        truckNumber.shouldHave(text(atStatus));
        truckNumber.shouldHave(text(atMake));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atModel));
        truckNumber.shouldHave(text(atYear));

        // *** Редагування Truck вкладка General фрейму Add truck ***
        truckNumber.$(".glyphicon-pencil").click();

        // Редагування дані редагування вкладка General
        String atPlateNumberEdit = globalName + "Plate Number 2";
        String atPlateStateEdit = "PL";
        String atModelEdit = globalName + "Volvo 2";
        String atColorEdit = globalName + "Green 2";
        String atYearEdit = "2024";
        String atMakeEdit = globalName + "Make 1";
        String atZipEdit = "76827";
        String atCityEdit = "Brookesmith";
        String atStateEdit = "TX";
        String atOwnerEdit = "AutoTestOwner2 INC";
        String atNoteEdit = "Note truck 2";
        String atStatusEdit = "Available On";

//        String atPlateNumberEdit = "Plate auto test 2" + randomNumber;
//        String atPlateStateEdit = "PL";
//        String atModelEdit = "Volvo auto test 2";
//        String atColorEdit = "Green auto test 2";
//        String atYearEdit = "2022";
//        String atMakeEdit = "Make truck auto test 2";
//        String atZipEdit = "76827";
//        String atCityEdit = "Brookesmith";
//        String atStateEdit = "TX";
//        String atOwnerEdit = "AutoTestOwner2 INC";
//        String atNoteEdit = "Note truck 2";
//        String atStatusEdit = "Available On";

        // Вводить дані редагування
        $(".bootstrap-dialog-title").shouldHave(text("Update truck"), Duration.ofSeconds(20));
        $("#trucks-vin_number").setValue(atVinNumber);
        $("#trucks-plate_number").setValue(atPlateNumberEdit);
        $("#trucks-plate_state").setValue(atPlateStateEdit);
        $("#trucks-model").setValue(atModelEdit);
        $("#trucks-color").setValue(atColorEdit);
        $("#trucks-year").setValue(atYearEdit);
        $("#trucks-make").setValue(atMakeEdit);

        // Редагування календар Date When Will Be There
        $(".kv-datetime-picker").click();
        Calendar.setDateTime(1);

        $("#trucks-status").selectOption("Available On");
        $("#trucks-title_status").selectOption("Salvage");
        $("#trucks-cab_type").selectOption("Day cab");

        // Редагування пошук по Zip коду
        $("#trucks-last_zip").setValue(atZipEdit);
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value(atCityEdit));
        $("#trucks-last_state").shouldHave(value(atStateEdit));

        // Редагування поле Owner
        $("#select2-owner_id-update-container").click();
        $(".select2-search__field").setValue("AutoTestOwner");
        $$("li.select2-results__option")
                .findBy(text(atOwnerEdit))
                .click();

        // Редагування календар Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(1);

        // Редагування календар Annual Vehicle
        $("#trucks-annual_vehicle_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(1);

        // Редагування поле Note
        $("#trucks-note").setValue(atNoteEdit);

        // Редагування Submit фрейм Update truck
        $("#update_truck_send").click();

        // Тост вспливайка
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Truck sucessfully updated"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // *** Перевіряє відредаговані дані Truck в списку ***
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible).setValue(atTruckNumber).pressEnter();

        SelenideElement rowTruck = $$("table.color-table-bigtruck tbody tr")
                .get(0)
                .shouldHave(text(atTruckNumber));

        rowTruck.shouldHave(text(atTruckNumber));
        rowTruck.shouldHave(text(atVinNumber));
        rowTruck.shouldHave(text(atPlateNumberEdit));
        rowTruck.shouldHave(text(atStatusEdit));
        rowTruck.shouldHave(text(atMakeEdit));
        rowTruck.shouldHave(text(atModelEdit));
        rowTruck.shouldHave(text(atModelEdit));
        rowTruck.shouldHave(text(atYearEdit));

        // *** Перевіряє відредаговані дані Truck фрейм Truck owner ***

        // Клік по кнопці око
        rowTruck.$(".glyphicon-eye-open").click();
        $("#view_truck").shouldBe(visible, EXPECT_GLOBAL);

        // Перевіряє відредаговані дані Truck в View truck
        $("table#w0").$$("tr").findBy(text("Vin Number"))
                .$$("td").first().shouldHave(text(atVinNumber));

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
        $("#view_truck").shouldNotBe(visible, EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
