package web.expedite.loadBoard;

import com.codeborne.selenide.Selenide;
import web.Login;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class TestCase6LoadBoard extends Login {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void rateCustomersDriver() throws InterruptedException {

        System.out.println("TestCase6LoadBoard - Start");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();
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
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendarNew(1, 0);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendarNew(2, 1);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendarNew(3, 2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendarNew(4, 3);

        //pallets shippers
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

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
        $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(20));

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("Load dispatch sucessfully added"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        System.out.println("TestCase6LoadBoard - Test Pass");
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

        if (switchMonth) {
            Selenide.executeJavaScript("arguments[0].click();", $$(".datetimepicker-days .next").get(numberCalendar));
        }

        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).click();

        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
    }

    public void inputCalendarNew(int introductionDay, int numberCalendar){

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

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
