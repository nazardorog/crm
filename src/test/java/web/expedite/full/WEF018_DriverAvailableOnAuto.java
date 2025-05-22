package web.expedite.full;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.io.File;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class WEF018_DriverAvailableOnAuto {

    //поточний час по Мексиці
    LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Mexico_City"));
    int day = now.getDayOfMonth();
    int hourNotRounded = now.getHour();
    int hour = ((hourNotRounded + 1) / 2) * 2;
    int minute = (now.getMinute() / 5) * 5;
//    LocalDateTime statusDateDriver = now.plusDays(3).withHour(hour).withMinute(minute);
//    LocalDateTime statusDateDriver = now.plusDays(3).withHour(hour).withMinute(minute);
    DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
//    String dateToShippersDestination = statusDateDriver.format(formatDate);

    @Test
    public void availableOnAuto() throws InterruptedException {

        // Login
        GlobalLogin.login("exp_disp1");

        //перед тестом ставить драйверу Not Available
        $(".expedite-fleet-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".expedite-fleet-user").click();
        $("body").click();

        $(By.name("TrucksSearch[filter_driver_name]")).setValue("AutoTest Driver2").pressEnter();
        $(".driver-name").shouldHave(text("AutoTest Driver2"));
        $(".glyphicon-pencil").click();
        $("#trucks-status").selectOption("Not Available");
        $("#trucks-last_zip").setValue("83210");
        $("#zipFillBtn").click();
        $("#update_truck_send").click();

        //тест
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker2");
        $(".select2-results__options").shouldHave(text("Auto test broker2")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent2 ")).click();

        //Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test availableOn1");
        $(".select2-results").shouldHave(text("Auto test availableOn1")).click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test availableOn2");
        $(".select2-results__options").shouldHave(text("Auto test availableOn2")).click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(day))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 1 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 1, minute))).click(); // Вибираємо хвилини

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 3 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 3, minute))).click(); // Вибираємо хвилини

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTimeMexico(2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTimeMexico(3);

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //other data
        Random random = new Random();
        String reference = String.format("%3d", random.nextInt(10000000)) ;
        $("#loads-reference").setValue(reference);
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //load file
        Thread.sleep(4000);
        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load").find(".modal-footer-button .fa-files-o").click();

        executeJavaScript("arguments[0].scrollTop = 0;", modal);
        File file = new File("C:/Empire/pdf1.pdf");
        $("#loaddocuments-0-file").uploadFile(file);

        if (!$("#loaddocuments-0-type").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#loaddocuments-0-type"));
        }
        $("#loaddocuments-0-type").selectOption("BOL");

        if (!$("#load_documents_modal_pseudo_submit").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#load_documents_modal_pseudo_submit"));
        }
        $("#load_documents_modal_pseudo_submit").click();

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#add_load_send_old").click();

        //dispatch board
        executeJavaScript("arguments[0].scrollTop = 0;", modal);
        $("#select2-load_truck_id-0-container").shouldBe(visible, Duration.ofSeconds(20)).click();
        $(".select2-search__field").setValue("0304");
        $(".select2-results__option--highlighted").shouldHave(text("0304")).click();
        $("#select2-load_truck_id-0-container").shouldHave(Condition.text("0304"));
        $("#select2-load_driver_id-0-container").shouldHave(Condition.text("AutoTest Driver2"));
        $("#select2-load_team_driver_id-0-container").shouldHave(Condition.text("Search for a team driver ..."));

        //приховуємо help блок
        SelenideElement helpBlock = $(".help-block");
        executeJavaScript("arguments[0].style.display='none';", helpBlock);

        //Calculate клік
        $(".btn.btn-default.pull-right").click();

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();

        //frame automatic status driver
        $("#dispatch_load_send").click();
        $("#set-automatic-status-link").shouldBe(enabled, Duration.ofSeconds(10)).click();
        $(".modal-wrapper-set-auto-status-text").shouldBe(enabled).shouldHave(text("Please note that the pick-up for this load is scheduled for  today. As a result, the truck's status will automatically change to 'Available On' in the delivery city three hours before the scheduled pick-up time."));
        $("#automatic_status_send").shouldBe(enabled).click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(20)).shouldHave(text("Status will be automatically change."));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        $("#dispatch_load_send").click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(20)).shouldHave(text("Load dispatch sucessfully added"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє статус Available On водія в Expedite Fleet
        $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(10));
        $(".expedite-fleet-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".expedite-fleet-user").click();
        $("body").click();

        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible, Duration.ofSeconds(10)).setValue("AutoTest Driver2").pressEnter();
        $(By.name("TrucksSearch[filter_driver_name]")).setValue("AutoTest Driver2").pressEnter();
        $(".truck-center-text").shouldHave(text("Available On"), Duration.ofSeconds(10));
//        $(".truck-date-when-there").shouldHave(text(dateToShippersDestination));
        $(".driver-name").shouldHave(text("AutoTest Driver2"));
        $(".city-state-zip").shouldHave(text("Philadelphia, PA 19019"));

        //go home
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {
        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
