package web.LoadBord;

import web.Login;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class TestCase18LoadBoard extends Login {

    @Test
    public void editCargo () throws InterruptedException {
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(1)).click();
        $("#new_load").click();

        //прибрати віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        //поточний час по серверу
        LocalDateTime now = LocalDateTime.now();
        int day = now.getDayOfMonth();
        int hour = now.getHour();
        int month = now.getMonthValue();
        int minute = (now.getMinute() / 5) * 5;

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
//        $(".select2-results__options").shouldHave(text("Auto test broker")).click();
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
//        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();
//        $(".select2-results__options").shouldHave(text("Auto test shipper 2")).click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(day))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 3))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click();
        Thread.sleep(1000);// Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //input other data
        Random random = new Random();
        String reference = String.format("%3d", random.nextInt(10000000)) ;
        $("#loads-reference").setValue(reference);
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //скролить до низу, клік по завантаженню файлів
        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        Thread.sleep(3000);

        //load file
        //скролить до верху
        executeJavaScript("arguments[0].scrollTop = 0;", modal);
        File file = new File("C:/Empire/pdf1.pdf");
        $("#loaddocuments-0-type").selectOption("BOL");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //файл pdf, тип POD
        $("#loaddocuments-0-file").uploadFile(file);
        $("#loaddocuments-0-type").selectOption("POD");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //клік по Submit фрейму додавання файлів
        $("#load_documents_modal_pseudo_submit").click();

        //скрол вниз клік по Submit фрейму додавання вантажу
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").click();

    //dispatch board
        $("#select2-load_truck_id-0-container")
                .shouldBe(visible, Duration.ofSeconds(20))
                .click();

        //отримує номер вантажу
        String dispatchLoad = $("#load_dispatch .modal-title").getText();
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        //вводить Truck
        $(".select2-search__field").setValue("0303");

        //приховуємо help блок
        SelenideElement helpBlock = $(".help-block");
        executeJavaScript("arguments[0].style.display='none';", helpBlock);

        //перевіряє що Truck вибраний вірно
        $(".select2-results__option--highlighted").shouldHave(text("0303"), Duration.ofSeconds(10)).click();

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();
        $("#dispatch_load_send").click();
        System.out.println("Номер вантажу: " + loadNumber);

    //редагування в лоад борд вантажу
//        String loadNumber = "30706";
        //знаходить створений вантаж
        $(".content-header").shouldHave(text("Load Board"));
        Thread.sleep(1000);
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).sendKeys(Keys.ENTER);

        $("td a.view_load").shouldHave(text(loadNumber));
        $("td a.view_truck").shouldHave(text("0303"));
        $$("td.col_driver_carrier a.view_driver").get(0).shouldHave(exactText("Auto Test"));
        $$("td.col_driver_carrier a.view_driver").get(1).shouldHave(exactText("Auto Test2"));
        $("td a.view_owner").shouldHave(text("Autotest 1 Owner"));
        $("td a.view_broker").shouldHave(text("Auto test broker"));

        $(".view_pick_up_location").shouldHave(text("Kansas City, MO 64110"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 1 Plt 1 Pcs 1"));
//        $$("tr[data-pk='loadNumber'] span.pull-right").get(0).shouldHave(text(month + "/" + day));
//        $$("tr[data-pk='loadNumber'] span.pull-right").get(1).shouldHave(text(month + 1 + "/" + day));

        $(".view_delivery_location").shouldHave(text("New York, NY 10002"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 1 Plt 1 Pcs 1"));
//        $$("tr[data-pk='loadNumber'] span.pull-right").get(2).shouldHave(text("03/27"));
//        $$("tr[data-pk='loadNumber'] span.pull-right").get(3).shouldHave(text("03/28"));

//        $(".col_check_call span.pull-right").shouldHave(text("03/25 08:20"));

        //редагування вантажу
        $("#main-loads-grid .dropdown-toggle").click();
        $$(".dropdown-menu-right li").findBy(text("Edit Load")).click();

        $("#delete_load_broker").click();
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker10");
        $(".select2-results__options").shouldHave(text("Auto test broker10")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent39")).click();


        //Origin Shippers
        $("#shippers-origin-sortable .delete_load_shipper").click();
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper5");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper5"))
                .click();

        //Destination Shippers
        $("#shippers-destination-sortable .delete_load_shipper").click();
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper6");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper6"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
//        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(day - 1))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 1))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        dateElement.findBy(exactText(String.valueOf(day + 2))).click();
        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click();
        Thread.sleep(1000);// Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click();

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("2");
        $("#loadspickuplocations-0-weight").setValue("2");
        $("#loadspickuplocations-0-pcs").setValue("2");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("2");
        $("#loadsdeliverylocations-0-weight").setValue("2");
        $("#loadsdeliverylocations-0-pcs").setValue("2");

//        SelenideElement modal = $(".modal-dialog");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#update_load_send").click();

        //перевіряє відредаговані дані
        //знаходить створений вантаж
        Thread.sleep(2000);
        refresh();

////        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10)).click();
//        $(".content-header").shouldHave(text("Load Board"));
//        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);

//        $("#main-loads-grid .dropdown-toggle").click();
//        $$(".dropdown-menu-right li").findBy(text("update_load")).click();


        $("td a.view_load").shouldHave(text(loadNumber));
        $("td a.view_truck").shouldHave(text("0303"));
        $$("td.col_driver_carrier a.view_driver").get(0).shouldHave(exactText("Auto Test"));
        $$("td.col_driver_carrier a.view_driver").get(1).shouldHave(exactText("Auto Test2"));
        $("td a.view_owner").shouldHave(text("Autotest 1 Owner"));
        $("td a.view_broker").shouldHave(exactText("Auto test broker10"));

        $(".view_pick_up_location").shouldHave(text("Washington, DC 20016"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 2 Plt 2 Pcs 2"));
        $$("span.pull-right").get(0).shouldHave(text("03/25"));
        $$("span.pull-right").get(1).shouldHave(text("03/26"));

        $(".view_delivery_location").shouldHave(text("Monroeville, PA 15146"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 2 Plt 2 Pcs 2"));
        $$("span.pull-right").get(2).shouldHave(text("03/27"));
        $$("span.pull-right").get(3).shouldHave(text("03/28"));
//        $$("tr[data-pk='30706'] span.pull-right").get(3).shouldHave(text("03/28"));

//        $(".col_check_call span.pull-right").shouldHave(text("03/25 08:20"));






        System.out.println("TestCase18LoadBoard - OK");
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }

}
