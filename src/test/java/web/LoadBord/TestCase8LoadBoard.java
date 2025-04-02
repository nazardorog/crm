package web.LoadBord;

import com.codeborne.selenide.Selenide;
import web.Login;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestCase8LoadBoard extends Login {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void originalDestinationPltsWghtPcs(){

        System.out.println("TestCase8LoadBoard - Start");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").click();

        //прибрати віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $$(".select2-results__options")
                .findBy(text("Auto test broker"))
                .click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent ")).click();

        //Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 1"))
                .click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(1, 0);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(2, 1);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(3, 2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(4, 3);

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

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, переключаємо календарь на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        if (switchMonth) {
            Selenide.executeJavaScript("arguments[0].click();", $$(".datetimepicker-days .next").get(numberCalendar));
        }

        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).click();

        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
    }
}
