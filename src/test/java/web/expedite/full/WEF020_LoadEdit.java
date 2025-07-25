package web.expedite.full;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.sleep;

public class WEF020_LoadEdit {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void edit() throws InterruptedException {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        //broker
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
        Calendar.setDateTime(1);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

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
        $("#loaddocuments-0-type").selectOption("BOL");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //файл pdf, тип POD
        String fileName = "1pdf.pdf";
        File file = new File(downloadsFolder + fileName);
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
        $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(10));

        //перевіряє дані створеного вантажу
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

        $(".view_delivery_location").shouldHave(text("New York, NY 10002"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 1 Plt 1 Pcs 1"));

        //редагування вантажу
        $("#main-loads-grid .dropdown-toggle").shouldBe(visible,enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Edit Load")).shouldBe(enabled, Duration.ofSeconds(10)).click();

        //вводить нові дані Broker, Agent, Origin Shippers, Destination Shippers
        $("#delete_load_broker").click();
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker10");
        $(".select2-results__options").shouldHave(text("Auto test broker10")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent39")).click();

        //Origin Shippers
        $("#shippers-origin-sortable .delete_load_shipper").click();
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper7");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper7"))
                .click();

        //Destination Shippers
        $("#shippers-destination-sortable .delete_load_shipper").click();
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper8");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper8"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(5);

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

        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $(".content-header").shouldHave(text("Load Board"));
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);

        $("#main-loads-grid .dropdown-toggle").shouldBe(visible,enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Edit Load")).shouldBe(visible).click();

        $("td a.view_load").shouldHave(text(loadNumber));
        $("td a.view_truck").shouldHave(text("0303"));
        $$("td.col_driver_carrier a.view_driver").get(0).shouldHave(exactText("Auto Test"));
        $$("td.col_driver_carrier a.view_driver").get(1).shouldHave(exactText("Auto Test2"));
        $("td a.view_owner").shouldHave(text("Autotest 1 Owner"));
        $("td a.view_broker").shouldHave(exactText("Auto test broker10"));

        $(".view_pick_up_location").shouldHave(text("Washington, DC 20016"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 2 Plt 2 Pcs 2"));

        LocalDateTime futureDate = now.plusDays(2);
        String formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        String selector = String.format("tr[data-pk='%s'] span.pull-right", loadNumber);
        $$(selector).get(0).shouldHave(text(formattedDate));

        futureDate = now.plusDays(3);
        formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(selector).get(1).shouldHave(text(formattedDate));

        $(".view_delivery_location").shouldHave(text("Monroeville, PA 15146"));
        $("span.small-txt.big-truck-none-bold").shouldHave(text("Wt 2 Plt 2 Pcs 2"));

        futureDate = now.plusDays(4);
        formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(selector).get(2).shouldHave(text(formattedDate));

        futureDate = now.plusDays(5);
        formattedDate = String.format("%02d/%02d", futureDate.getMonthValue(), futureDate.getDayOfMonth());
        $$(selector).get(3).shouldHave(text(formattedDate));
    }



    public void scrollDown(SelenideElement modal, SelenideElement target) {

        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
