package web.expedite.loadBoard;

import com.codeborne.selenide.Selenide;
import web.Login;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestCase11LoadBoard extends Login {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

        @Test
        public void assingUser() throws InterruptedException {

            System.out.println("TestCase11LoadBoard - Start");

            $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30));
            $("#new_load").shouldBe(enabled).click();

            //прибрати віджет чат
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

            //brocker
            $("#add_load").shouldBe(visible, Duration.ofSeconds(5)).shouldHave(text("New load"));
            $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
            $("#select2-broker_search-container").shouldBe(visible).click();
            $(".select2-search__field").setValue("Auto test broker");
            $(".select2-results__options").shouldHave(text("Auto test broker")).click();
            $$("select#loads-agent_id option").findBy(text("Auto test agent")).click();

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
            $("#loadsdeliverylocations-0-pallets").setValue("1");
            $("#loadsdeliverylocations-0-weight").setValue("1");
            $("#loadsdeliverylocations-0-pcs").setValue("1");

            //скрол, клік Submit
            SelenideElement modal = $("#add_load");
            executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

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
            $("#add_load_send_old").click();

//dispatch board
            executeJavaScript("arguments[0].scrollTop = 0;", modal);

            $("#select2-load_truck_id-0-container")
                .shouldBe(visible, Duration.ofSeconds(30))
                .click();

            //отримуємо номер вантажу
            String dispatchLoad = $("#load_dispatch .modal-title").getText();
            String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();
            System.out.println("Номер вантажу: " + loadNumber);

            //вводимо Truck
            $(".select2-search__field").setValue("0303");
            $(".select2-results__option--highlighted").shouldHave(text("0303")).click();

            //приховуємо help блок
            SelenideElement helpBlock = $(".help-block");
            executeJavaScript("arguments[0].style.display='none';", helpBlock);

            if (!$("#loads-load_type label").isDisplayed()){ //scroll
                scrollDown($("#add_load"), $("#loads-load_type label"));
            }
            $$("#loads-load_type label").findBy(Condition.text("Board")).click();

            //вибираємо юзера для асайна
            $("#loadassignedusers-user_id").shouldBe(visible).selectOption("Auto Test user1");
            $("#loadassignedusers-user_id").getSelectedOption().shouldHave(text("Auto Test user1"), Duration.ofSeconds(5));
            Thread.sleep(4000);
            $("#load_assigned_users_send").click();

            //поточний час по Мексиці
            Thread.sleep(4000);
            String currentTime = mexicoTime();
            $("#loadassignedusers-user_id").selectOption("Auto Test user2");
            Thread.sleep(4000);
            $("#load_assigned_users_send").click();

            //перевірка чи заасайнився юзер
            $$(".table-assigned-users tr").findBy(text("User")).shouldBe(visible);
            $$(".table-assigned-users tr").findBy(text("Auto Test user1")).shouldBe(visible);
            $$(".table-assigned-users tr").findBy(text(currentTime)).shouldBe(visible);

            $$(".table-assigned-users tr").findBy(text("Auto Test user2")).shouldBe(visible);
            $$(".table-assigned-users tr").findBy(text("Auto Test user2")).shouldBe(visible);
            $$(".table-assigned-users tr").findBy(text(currentTime)).shouldBe(visible);

            //видаляємо Auto Test user1
            $(".table-assigned-users .delete_user_assignment").click();
            $$(".table-assigned-users tr").findBy(text("Auto Test user1")).should(exist);
                    $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(10));

            //перевірка в лоад борд через Load assigned чи заасайнився юзер до грузу
            $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
            $(".content-header").shouldHave(text("Load Board"));
            $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);
            $("a.view_load").shouldBe(text(loadNumber), Duration.ofSeconds(10));
            $("td.our_pro_number i.glyphicon.glyphicon-link").click();

            $$("td").findBy(Condition.text("Auto Test user2" ))
                    .shouldHave(Condition.text("Auto Test user2"), Duration.ofSeconds(5));
            $$("td").findBy(text(currentTime)).shouldBe(visible);

            $(".modal-view-item .close").click();
            executeJavaScript("arguments[0].scrollTop = 0;", modal);

            System.out.println("TestCase11LoadBoard - Test Pass");
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

        public String mexicoTime() {
            // Поточний час Мехіко
            LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Mexico_City"));

            // Приводимо до формату "MM/dd/yyyy HH:mm"
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

            return now.format(formatter);
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {
            while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }

}
