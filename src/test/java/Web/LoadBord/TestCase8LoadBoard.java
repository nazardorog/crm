package Web.LoadBord;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestCase8LoadBoard {

    @Test(dependsOnMethods = {"Web.Login.loginWeb"})
    public void originalDestinationPltsWghtPcs(){

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10));
        $("#new_load").click();

        //прибрати віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = (now.getMinute() / 5) * 5;

        //brocker
        $("#select2-broker_search-container").shouldBe(Condition.visible, Condition.enabled).click();
        $(".select2-search__field").setValue("Auto test broker");
        $(".select2-results__options").shouldHave(text("Auto test broker")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent")).click();

        //shippers pickup
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //calendar shippers pickup from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 1))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar shippers pickup to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 1))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 2 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 2, minute))).click(); // Вибираємо хвилини

        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $(".select2-results__options").shouldHave(text("Auto test shipper 2")).click();

        //calendar shippers destination from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 2))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //calendar shippers destination to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 2))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 2 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 2, minute))).click();

        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //pallets shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets destination
        $("#loadsdeliverylocations-0-pallets").setValue("2");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //скрол, клік Submit
        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();

    //валідність Plts
        $(".load_pallets_error").shouldHave(Condition.text("The Pallets of the shipment is not equal to the Pallets of the destination:1/2"));

        //скрол вверх
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //болдер чи став тільки червоним Original Plts
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        //болдер чи став тільки червоним Destination Plts
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

    //валідність Weight
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("2");

        //скрол вниз
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();
        $(".load_pallets_error").shouldHave(Condition.text("The weight of the shipment is not equal to the weight of the destination: 1/2"));

        //скрол вверх
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //болдер чи став тільки червоним Original Weight
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

        //болдер чи став тільки червоним Destination Weight
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));

    //валідність Pcs
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("2");

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();
        $$(".load_pallets_error").get(0).shouldHave(Condition.text("The total number of Pcs on pickup must be equal to the total number of Pcs on delivery:1/2"));

        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //болдер чи став тільки червоним Original Pcs
        $("#loadspickuplocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadspickuplocations-0-pcs").closest(".form-group").shouldHave(Condition.cssClass("has-error"));

        //болдер чи став тільки червоним Destination Pcs
        $("#loadsdeliverylocations-0-pallets").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-weight").closest(".form-group").shouldNotHave(Condition.cssClass("has-error"));
        $("#loadsdeliverylocations-0-pcs").closest(".form-group").shouldHave(Condition.cssClass("has-error"));

        $("#add_load .close").click();

        System.out.println("TestCase8LoadBoard - OK");
    }
}
