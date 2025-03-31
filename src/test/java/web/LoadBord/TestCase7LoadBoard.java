package web.LoadBord;

import web.Login;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class TestCase7LoadBoard extends Login {

    @Test
    public void dateValidationShippers(){

        System.out.println("TestCase7LoadBoard - Start");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(20));
        $("#new_load").click();
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $$(".select2-results__options")
                .findBy(text("Auto test broker"))
                .click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent ")).click();
        //origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //origin pallets
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //shippers Distanation
        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //pallets destination
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //calendar Origin Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(20 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 20, 30))).click(); // Вибираємо хвилини

        //calendar Origin Date To
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(20 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 20, 20))).click(); // Вибираємо хвилини

        //calendar Destination Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(21 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 21, 00))).click();

        //calendar Destination Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(22 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 22, 00))).click();

        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#add_load_send_old").click();
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

    //перевірка валідності Origin Date To
        //orign Date To
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Date To must be greater or equal to \"Date From\"."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));

        // не відображається origin Date From help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // не відображається destination Date From help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // не відображається destination Date To help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

    //перевірка валідності Destination Date From
        //підготовка данних

        //calendar Origin Date To
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(22 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 22, 00))).click(); // Вибираємо хвилини

        //calendar Destination Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(20 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 20, 20))).click();

        //скрол, клік Submit, склол
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#add_load_send_old").click();
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //тест
        //destination Date From
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Date from must be greater than the first Date From in Origin section"))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));

        //не відображається origin Date From help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        //не відображається origin Date To help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        //не відображається destination Date To help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

    //перевірка валідності Destination Date To
        //підготовка данних

        //calendar Destination Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(21 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 21, 30))).click();

        //calendar Destination Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(21 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", 21, 20))).click();

        //скрол, клік Submit, склол
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#add_load_send_old").click();
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //destination Date To
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Date To must be greater or equal to \"Date From\"."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));

        // не відображається origin Date From help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        //не відображається origin Date To help-block
        $(".origin-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date To"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        // не відображається destination Date From help-block
        $(".destination-block")
                .$$(".pick-up-button")
                .findBy(Condition.text("Date From"))
                .find(".help-block")
                .shouldNotBe(Condition.visible);

        $("#add_load .close").click();

        System.out.println("TestCase7LoadBoard - OK");
    }
}
