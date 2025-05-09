package web.bigTruck.smoke.trailer;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.Condition;
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
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WBS028_TrailerCreateDryVan {

    // Click Up:
    // CRM SEMI Truck
    // Trailers
    // 1. Создание трейлера Dry van

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    String globalName = GlobalGenerateName.globalName();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void createDryVan() {

        // Login
        GlobalConfig.OPTION_LOGIN = "big";
        WebDriverConfig.setup();
        LoginHelper.login();

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

        String atTrailerNumber = globalName + "Trailer Number 1";
        String atVinNumber = "VIN12345678999" + randomNumber;
        String atPlateNumber = globalName + "Plate auto test 1";
        String atPlateNumberState = "PL";
        String atMake = globalName + "Make 1";
        String atModel = globalName + "Volvo auto test 1";
        String atTypeTrailer = "53' Dry Van";
        String atYear = "2025";
        String atOwner = "AutoTestOwner1 INC";
        String atNote = globalName + "Note 1";
        String atDescription = globalName + "Description 1";

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
        Calendar.setDate(0);

        //календар Inspection Expiration date
        $("#trailers-registration_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(0);

        //поле Owner
        $("#select2-owner_id-create-container").click();
        $(".select2-search__field").setValue("AutoTestOwner");
        $$("li.select2-results__option")
                .findBy(text(atOwner))
                .click();

        //поле Note
        $("#trailers-note").setValue(atNote);

        // *** Вкладка Documents фрейму Add trailers ***
        $("a[href='#documents']").shouldBe(visible).click();

        //завантажує файл
        String filePath;
        if (new File("/.dockerenv").exists()) {
            filePath = "/app/Empire/1pdf.pdf";  // для Docker
        } else {
            filePath = "C:\\Empire\\1pdf.pdf";  // для локально
        }
        File file = new File(filePath);

        $("#trailerdocuments-0-file").uploadFile(file);

        //поле Description
        $("#trailerdocuments-0-description").setValue(atDescription);

        Selenide.sleep(5000);

        //клік по кнопці Submit фрейму Add trailer
        $("#add_trailer_send").shouldBe(clickable).click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(40));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("trailer sucessfully added"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє створений Trailer в списку
        $("input[name='TrailersSearch[trailer_number]']").shouldBe(visible).setValue(atTrailerNumber).pressEnter();

        SelenideElement rowTrailer = $$("table.table-striped tbody tr")
                .get(0)
                .shouldHave(text(atTrailerNumber));

        rowTrailer.shouldHave(text(atTrailerNumber));
        rowTrailer.shouldHave(text(atVinNumber));
        rowTrailer.shouldHave(text(atPlateNumber));
        rowTrailer.shouldHave(text(atMake));
        rowTrailer.shouldHave(text(atModel));
        rowTrailer.shouldHave(text(atTypeTrailer));
        rowTrailer.shouldHave(text(atYear));
        rowTrailer.shouldHave(text(atOwner));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}