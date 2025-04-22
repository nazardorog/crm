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
import static com.codeborne.selenide.Selenide.$;

public class TestCase15LoadBoard extends Login {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void sippersCoordinatesNotSet() throws InterruptedException {

        System.out.println("TestCase15LoadBoard - Start");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();

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
        $(".select2-search__field").setValue("AutoTestNotSet1");
        $(".select2-results").shouldHave(text("AutoTestNotSet1")).click();

        //перевіряє коректність вибору Origin Shippers
//        Thread.sleep(3000);
//        $("#shippers-origin-sortable .shipper-locations-name").shouldHave(text("AutoTestNotSet1"));
//        $("#shippers-origin-sortable .shipper-locations-street").shouldHave(text("AutoTest Street1"));
//        $("#shippers-origin-sortable .shipper-locations-location").shouldHave(text("autoTest"));

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("AutoTestNotSet2");
        $(".select2-results__options").shouldHave(text("AutoTestNotSet2")).click();

        //перевіряє коректність вибору Destination Shippers
        $("#shippers-destination-sortable .shipper-locations-name").shouldHave(text("AutoTestNotSet2"));
        $("#shippers-destination-sortable .shipper-locations-street").shouldHave(text("AutoTest Street2"));
        $("#shippers-destination-sortable .shipper-locations-location").shouldHave(text("autoTest"));

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        inputCalendar(1, 0);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        inputCalendar(2, 1);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        inputCalendar(3, 2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        inputCalendar(4, 3);

        //pallets Origin Shippers
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //other data
        $("#loads-reference").setValue("1122334");
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
        $("#add_load_send_old").shouldHave(visible).click();

//        $$(".load-error div").shouldHave(sizeGreaterThanOrEqual(2));
        $$(".load-error div")
                .get(0)
                .shouldHave(text("You cannot add this load because shipper-receiver’s address is not valid."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
        $$(".load-error div")
                .get(1)
                .shouldHave(exactText("You cannot add this load because shipper-receiver’s address is not valid."))
                .shouldHave(Condition.cssValue("color", "rgba(221, 75, 57, 1)"));
    }

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календарь на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        if (switchMonth) {
            Selenide.executeJavaScript("arguments[0].click();", $$(".datetimepicker-days .next").get(numberCalendar));
        }

        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).shouldBe(enabled).click();

        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
