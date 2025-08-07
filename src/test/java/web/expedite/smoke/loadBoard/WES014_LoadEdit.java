package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.CloseWebDriver;


import utilsWeb.configWeb.GlobalLogin;
import utilsWeb.jenkins.CustomName;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

@Listeners(utilsWeb.commonWeb.Listener.class)
@Epic("Expedite")
@Feature("Smoke")
public class WES014_LoadEdit {

    @Test
    public void edit() throws InterruptedException {

        // Назва класу для Allure
        CustomName.getDescription();

        // Login
        GlobalLogin.login("exp_disp1");

        // Відкриває New Load
        $(".logo-mini-icon").shouldBe(enabled, EXPECT_GLOBAL);
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // Data for creating a Load
        final String atBroker = "at_Broker1";
        final String atBrokerAgent = "Auto test agent ";
        final String atOwner = "Autotest 1 Owner ";
        final String atShippersOrigin = "Auto test shipper 1";
        final String atShippersDist = "Auto test shipper 2";
        final String atReferenceNumber = "10000000";
        final String atCustomersRate = "100000";
        final String atCarrierDriverRate = "80000";
        final String atTruck = "0303";
        final String atDriver = "Auto Test";
        final String atTeamDriver = "Auto Test2";

        // Broker
        $("#loads-form-create").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(clickable).click();
        $(".select2-search__field").setValue(atBroker);
        $$(".select2-results__options").findBy(text(atBroker)).click();
        $$("select#loads-agent_id option").findBy(text(atBrokerAgent)).click();

        // Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue(atShippersOrigin);
        $$("li.select2-results__option").findBy(text(atShippersOrigin)).click();

        // Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue(atShippersDist);
        $$("li.select2-results__option").findBy(text(atShippersDist)).click();

        // Calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        // Calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        // Pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        // Pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        // Input other data
        $("#loads-reference").setValue(atReferenceNumber);
        $("#loads-rate-disp").setValue(atCustomersRate).pressEnter();
        $("#loads-carrier_rate-disp").setValue(atCarrierDriverRate).pressEnter();

        // Download file
        File file = new File(downloadsFolder + "/1pdf.pdf");
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        $("#load_documents_modal").shouldBe(visible, EXPECT_GLOBAL);

        $("#loaddocuments-0-file").uploadFile(file);
        $("#loaddocuments-0-type").selectOption("POD");

        // Клік Apply фрейму додавання файлів
        $("#load_documents_modal_pseudo_submit").click();

        // Клік по кнопці Submit на фрейм New Load
        $("#add_load_send_old").click();

        // Dispatch board
        $("#load_dispatch").shouldBe(visible, EXPECT_GLOBAL);

        // Отримує номер вантажу
        String dispatchLoad = $("#load_dispatch .modal-title").shouldBe(visible, EXPECT_GLOBAL).getText();
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        // Водить Truck
        $("#select2-load_truck_id-0-container").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".select2-search__field").setValue(atTruck);
        $$("li.select2-results__option").findBy(text(atTruck)).click();
        $("#select2-load_truck_id-0-container").shouldHave(text(atTruck), EXPECT_GLOBAL);

        // Remove help block
        boolean helpBlock = $(".help-block").shouldBe(visible, EXPECT_5).isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();
        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Load dispatch sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє дані створеного вантажу
        $(".content-header").shouldHave(text("Load Board"));
        $("#main-loads-grid-filters").shouldBe(visible, EXPECT_GLOBAL);

        // Перевіряє створений Load в списку
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();

        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoad.$("td a.view_load").shouldHave(text(loadNumber));
        rowLoad.$("td a.view_truck").shouldHave(text(atTruck));
        rowLoad.$$("td a.view_driver").get(0).shouldHave(text(atDriver));
        rowLoad.$$("td a.view_driver").get(1).shouldHave(text(atTeamDriver));
        rowLoad.$("td a.view_owner").shouldHave(text(atOwner));
        rowLoad.$("td a.view_broker").shouldHave(text(atBroker));
        rowLoad.$("td a.view_pick_up_location").shouldHave(text("Kansas City, MO 64110"));
        rowLoad.$$(".loads-locations").get(0).shouldHave(text("Wt 1 Plt 1 Pcs 1"));
        rowLoad.$("td a.view_delivery_location").shouldHave(text("New York, NY 10002"));
        rowLoad.$$(".loads-locations").get(1).shouldHave(text("Wt 1 Plt 1 Pcs 1"));

        // Редагування вантажу
        rowLoad.$("button.dropdown-toggle").shouldBe(clickable).click();
        rowLoad.$(".dropdown-menu-right").shouldBe(visible, EXPECT_GLOBAL);
        rowLoad.$$(".dropdown-menu-right li").findBy(text("Edit Load")).click();

        // Data for Edit a Load
        final String atBrokerEdit = "at_Broker2";
        final String atBrokerAgentEdit = "Auto test agent2";
        final String atShippersOriginEdit = "Auto test shipper7";
        final String atShippersDistEdit = "Auto test shipper8";

        // Перевіряє відкриття фрейм Update Load
        $("#update_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#broker_block").shouldBe(visible, EXPECT_GLOBAL);

        // Edit Broker
        $("#delete_load_broker").click();
        $("#select2-broker_search-container").shouldBe(clickable).click();
        $(".select2-search__field").setValue(atBrokerEdit);
        $$(".select2-results__options").findBy(text(atBrokerEdit)).click();
        $$("select#loads-agent_id option").findBy(exactText(atBrokerAgentEdit)).click();

        // Edit Origin Shippers
        $("#shippers-origin-sortable .delete_load_shipper").click();
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue(atShippersOriginEdit);
        $$("li.select2-results__option").findBy(text(atShippersOriginEdit)).click();

        // Edit Destination Shippers
        $("#shippers-destination-sortable .delete_load_shipper").click();
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue(atShippersDistEdit);
        $$("li.select2-results__option").findBy(text(atShippersDistEdit)).click();

        // Edit calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Edit calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Edit calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        // Edit calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(5);

        // Edit pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("2");
        $("#loadspickuplocations-0-weight").setValue("2");
        $("#loadspickuplocations-0-pcs").setValue("2");

        // Edit pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("2");
        $("#loadsdeliverylocations-0-weight").setValue("2");
        $("#loadsdeliverylocations-0-pcs").setValue("2");

        // Click Submit frame Update Load
        $("#update_load_send").shouldBe(clickable, EXPECT_GLOBAL).click();
        $("#update_load").shouldNotBe(visible, EXPECT_GLOBAL);

        // Load board знаходить створений вантаж
        refresh();
        $("#main-loads-grid-filters").shouldBe(visible, EXPECT_GLOBAL);
        $(".logo-mini-icon").shouldBe(enabled, EXPECT_GLOBAL);
        $(".content-header").shouldHave(text("Load Board"));
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);

        // Перевіряє відредаговані дані
        SelenideElement rowLoadEdit = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoadEdit.$("td a.view_load").shouldHave(text(loadNumber));
        rowLoadEdit.$("td a.view_truck").shouldHave(text(atTruck));
        rowLoadEdit.$$("td a.view_driver").get(0).shouldHave(text("Auto Test"));
        rowLoadEdit.$$("td a.view_driver").get(1).shouldHave(text("Auto Test2"));
        rowLoadEdit.$("td a.view_owner").shouldHave(text(atOwner));
        rowLoadEdit.$("td a.view_broker").shouldHave(text(atBrokerEdit));
        rowLoadEdit.$("td a.view_pick_up_location").shouldHave(text("Washington, DC 20016"));
        rowLoadEdit.$$(".loads-locations").get(0).shouldHave(text("Wt 2 Plt 2 Pcs 2"));
        rowLoadEdit.$("td a.view_delivery_location").shouldHave(text("Monroeville, PA 15146"));
        rowLoadEdit.$$(".loads-locations").get(1).shouldHave(text("Wt 2 Plt 2 Pcs 2"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
