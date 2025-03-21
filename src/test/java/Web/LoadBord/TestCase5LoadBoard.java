package Web.LoadBord;

import Web.Login;
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
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class TestCase5LoadBoard extends Login {

    @Test
    public void twoShippersOrignDestination() throws InterruptedException {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(20));
        $("#new_load").click();

        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = (now.getMinute() / 5) * 5;

        //прибрати віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        //brockers
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $(".select2-results__options").shouldHave(text("Auto test broker")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent")).click();

        //shippers pickup 1
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //calendar shippers pickup from 1
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 1))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar shippers pickup to 1
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").shouldBe(visible).click();
        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 2 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 2, minute))).click(); // Вибираємо хвилини

        //pallets shippers 1
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //shippers pickup 2
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $(".select2-results").shouldHave(text("Auto test shipper 2")).click();

        //calendar shippers pickup from 2
        $("#loadspickuplocations-1-date_from-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar shippers pickup to 2
        $("#loadspickuplocations-1-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 1))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 2 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 2, minute))).click(); // Вибираємо хвилини

        //pallets shippers 2
        $("#loadspickuplocations-1-weight").setValue("2");
        $("#loadspickuplocations-1-pallets").setValue("2");
        $("#loadspickuplocations-1-pcs").setValue("2");

        //input destination 1
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 3");
        $(".select2-results__options").shouldHave(text("Auto test shipper 3")).click();

        //calendar shippers destination from 1
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 2))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //calendar shippers destination to 1
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 2))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 6 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 6, minute))).click();

        //pallets shippers destination 1
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //input destination 2
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 4");
        $(".select2-results__options").shouldHave(text("Auto test shipper 4")).click();

        //calendar shippers destination from 2
        $("#loadsdeliverylocations-1-date_from-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 2))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //calendar shippers destination to 2
        $("#loadsdeliverylocations-1-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
//        $$(".datetimepicker-days .day").findBy(exactText(String.valueOf(day + 2))).click(); // Вибираємо день
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + 6 + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour + 6, minute))).click();

        //pallets shippers destination 2
        $("#loadsdeliverylocations-1-pallets").setValue("2");
        $("#loadsdeliverylocations-1-weight").setValue("2");
        $("#loadsdeliverylocations-1-pcs").setValue("2");

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

        boolean statusDriver = $("#set-automatic-status-link").isDisplayed();
        if (statusDriver){
            $("#set-automatic-status-link").shouldHave(text("please set the automatic status change to Available On")).click();
            $(".text-set-status-link").shouldHave(text("Please note that the pick-up for this load is scheduled for  Thursday. As a result, the truck's status will automatically change to 'Available On' in the delivery city at 12:01 AM on the pick-up day."));
            $("#automatic_status_send]").click();
            $("#dispatch_load_send").click();
        }

        System.out.println("TestCase5LoadBoard - OK");
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
