package web.expedite.full.loadBoard;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalConfig;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class WEF013_DispatchAssignUser {

        @Test
        public void assignUser() throws InterruptedException {

            // Login
            GlobalConfig.OPTION_LOGIN = "expedite";
            WebDriverConfig.setup();
            LoginHelper.login();

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

            //отримує номер вантажу
            String dispatchLoad = $("#load_dispatch .modal-title").getText();
            String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

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
            $("#load_dispatch").shouldNotBe(visible, Duration.ofSeconds(20));

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

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
