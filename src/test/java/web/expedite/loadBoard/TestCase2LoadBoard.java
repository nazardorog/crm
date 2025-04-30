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
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;


public class TestCase2LoadBoard extends Login {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void fileTypeRateConfirmation() throws InterruptedException {

        System.out.println("TestCase2LoadBoard - Start");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();

        //Brocker
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

        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

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
        $("#loaddocuments-0-type").selectOption("Rate confirmation");

        if (!$("#load_documents_modal_pseudo_submit").isDisplayed()){ //scroll
            scrollDown($("#add_load"), $("#load_documents_modal_pseudo_submit"));
        }
        $("#load_documents_modal_pseudo_submit").click();

        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal); //scroll
        $("#add_load_send_old").click();

        //dispatch board
        $("#select2-load_truck_id-0-container")
                .shouldBe(visible, Duration.ofSeconds(30))
                .click();

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

        boolean statusDriver = $("#set-automatic-status-link").isDisplayed();
        if (statusDriver){
            $("#set-automatic-status-link").shouldHave(text("please set the automatic status change to Available On")).click();
            $(".text-set-status-link").shouldHave(text("Please note that the pick-up for this load is scheduled for  Thursday. As a result, the truck's status will automatically change to 'Available On' in the delivery city at 12:01 AM on the pick-up day."));
            $("#automatic_status_send]").click();
            $("#dispatch_load_send").click();
        }

        System.out.println("TestCase2LoadBoard - Test Pass");
    }

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, переключає календар на наступний місяць
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

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
