package web.expedite.full.loadBoard;

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

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class WEF014_DispatchCalculate {

    @Test
    public void calculate() throws InterruptedException {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $$(".select2-results__options")
                .findBy(text("Auto test broker"))
                .click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent ")).click();

        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper5");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper5"))
                .click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper6");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper6"))
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

        if (!$("#loaddocuments-0-type").isDisplayed()) { //scroll
            scrollDown($("#add_load"), $("#loaddocuments-0-type"));
        }
        $("#loaddocuments-0-type").selectOption("BOL");

        if (!$("#load_documents_modal_pseudo_submit").isDisplayed()) { //scroll
            scrollDown($("#add_load"), $("#load_documents_modal_pseudo_submit"));
        }
        $("#load_documents_modal_pseudo_submit").click();
        $("#add_load_send_old").click();

        //dispatch board
        $("#select2-load_truck_id-0-container")
                .shouldBe(visible, Duration.ofSeconds(20)).click();

        //отримує номер вантажу
        String dispatchLoad = $("#load_dispatch .modal-title").getText();
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        //вводимо Truck
        $(".select2-search__field").setValue("0303");
        $(".select2-results__option--highlighted")
                .shouldBe(visible, Duration.ofSeconds(20))
                .shouldHave(text("0303")).click();

        //приховуємо help блок
        SelenideElement helpBlock = $(".help-block");
        executeJavaScript("arguments[0].style.display='none';", helpBlock);

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();

        //Calculate перевірка даних
        $(".btn.btn-default.pull-right").click();

        $("h2").shouldHave(text("Totals"));
        $$("th").findBy(text("Weight:")).sibling(0).shouldHave(text("1 lb"));
        $$("th").findBy(text("Pallets:")).sibling(0).shouldHave(text("1"));
        $$("th").findBy(text("Pcs:"));
        $$("th").findBy(text("Length:"));
        $$("th").findBy(text("Rate:")).sibling(0).shouldHave(text("$1,000.00"));
        $$("th").findBy(text("Total:")).sibling(0).shouldHave(text("$800.00"));

        $("#dispatch_total_miles")
                .shouldHave(text("66"))
                .closest("tr").$("th").shouldHave(text("Total Miles:"));

        $("#dispatch_total_time")
                .shouldHave(matchText("1:[0-9]"))
                .closest("tr").$("th").shouldHave(text("Total Time:"));

        $("#dispatch_rate_mile")
                .shouldHave(text("$15.15"))
                .closest("tr").$("th").shouldHave(text("Rate / Mile:"));

        $("#dispatch_carrier_pay_mile")
                .shouldHave(text("$12.12"))
                .closest("tr").$("th").shouldHave(text("Carrier Pay/Mile:"));

        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(20));

        //перевіряємо в Load Bord Miles, Rate mile
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $(".content-header").shouldHave(text("Load Board"));
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);

        $x("//*[@id='main-loads-grid']//tbody/tr[1]/td[4]")
                .shouldHave(text("66mi"), text("$15.15"));

        //повертаємось на Load bord
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
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
