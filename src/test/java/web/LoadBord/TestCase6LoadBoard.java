package web.LoadBord;

import web.Login;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class TestCase6LoadBoard extends Login {

    @Test
    public void rateCustomersDriver() throws InterruptedException {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(20));
        $("#new_load").click();
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");


        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = (now.getMinute() / 5) * 5;

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $(".select2-results__options").shouldHave(text("Auto test broker")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent")).click();

        //origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //calendar shippers pickup from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar shippers pickup to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 1))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //pallets shippers
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //shippers Distanation
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $(".select2-results__options").shouldHave(text("Auto test shipper 2")).click();

        //calendar shippers destination from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 3))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 3))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //calendar shippers destination to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").shouldBe(visible).click();
        dateElement.findBy(exactText(String.valueOf(day + 3))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 3))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 2 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 2, minute))).click();

        //pallets destination
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        $("#loads-reference").setValue("1122334");

        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll down
        $("#add_load_send_old").click();

        executeJavaScript("arguments[0].scrollTop = 0;", modal); //scroll up

        //Rate Customers не може бути 0
        $(".field-loads-rate .help-block") //Customer Rate
                .shouldHave(Condition.visible)
                .shouldHave(Condition.text("Customers Rate cannot be blank."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
        $("#loads-rate")
                .shouldHave(Condition.cssValue("border-color", "rgb(221, 75, 57)"));

        //Rate Carrier/Driver може бути 0
        $("#loads-carrier_rate").
                shouldHave(Condition.cssValue("border-color", "rgb(0, 166, 90)"));
        $(".field-loads-carrier_rate .help-block")
                .shouldNotBe(Condition.visible);

        $("#loads-rate-disp").setValue("80000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("100000").pressEnter();

        //Rate Customers може бути менший Rate Carrier/Driver але з massage
        $("#loads-rate")
                .shouldHave(Condition.cssValue("border-color", "rgb(0, 166, 90)"));
        $(".Method… .help-block")
                .shouldNotBe(Condition.visible);

        $(".field-loads-carrier_rate .help-block")
                .shouldHave(Condition.visible)
                .shouldHave(Condition.text("Carrier/Driver Rate should be less Customers Rate. If you're not mistaken, ignore the warning."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
        $("#loads-carrier_rate")
                .shouldHave(Condition.cssValue("border-color", "rgb(221, 75, 57)"));


        //load file
        Thread.sleep(4000);
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

                $("#select2-load_truck_id-0-container")
                .shouldBe(visible, Duration.ofSeconds(20))
                .click();

        //dispatch board
        $(".select2-search__field").setValue("0303");
        $(".select2-results__option--highlighted").shouldHave(text("0303")).click();

        //приховуємо help блок
        SelenideElement helpBlock = $(".help-block");
        executeJavaScript("arguments[0].style.display='none';", helpBlock);

        if (!$("#loads-load_type label").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#loads-load_type label"));
        }
        $$("#loads-load_type label").findBy(Condition.text("Board")).click();

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#dispatch_load_send").click();

        System.out.println("TestCase6LoadBoard - OK");
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
