package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.DownloadDocument;
import utilsWeb.commonWeb.NewLoadExpedite;
import utilsWeb.configWeb.GlobalLogin;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WES032_LoadGetInvoice {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 21. Actions - Get Invoice

    @Test
    public void getInvoice() throws IOException {

        // Login. Create new load expedite
        GlobalLogin.login("exp_disp1");
        String atProNumber = NewLoadExpedite.loadExpedite();

        // Data for test
        final String atFileName = "invoice-pdf.pdf";

        // Логин под аккаунтингом
        $(".user-image-profile").shouldBe(clickable).click();
        $(".user-menu").shouldHave(cssClass("open"),EXPECT_GLOBAL);
        $(".exit-user-block").shouldBe(visible).click();
        GlobalLogin.login("exp_accounting1");
        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        // Вкладка Loads en Route
        $$("#loadTabs .li-tabs-home").findBy(text("Loads en Route")).click();
        SelenideElement activeTabLoadsEnRoute = $("div.tab-pane.active");
        activeTabLoadsEnRoute.$("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(atProNumber).pressEnter();

        // Дата доставки модальне вікно Drop Info
        activeTabLoadsEnRoute.$(".view_delivery_location").click();
        $("#view_item").shouldBe(visible, EXPECT_GLOBAL);
        $("#loadsdeliverylocations-date_delivery-datetime .kv-datetime-picker").click();
        Calendar.setDateTimeMexico(0);
        $("#view_item .close").click();
        $("#view_item").shouldNotBe(visible, EXPECT_GLOBAL);

        // Вкладка Loads en Route переводить вантаж в Loads Delivered
        activeTabLoadsEnRoute.click();
        SelenideElement loadsEnRoute = $("div.tab-pane.active");
        loadsEnRoute.$("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(atProNumber).pressEnter();
        SelenideElement rowLoadsEnRoute = $$("table.table-striped tbody tr").get(0).shouldHave(text(atProNumber));
        rowLoadsEnRoute.$("td a.view_load").shouldHave(text(atProNumber), EXPECT_5);
        rowLoadsEnRoute.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowLoadsEnRoute.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        ElementsCollection dropDownLoadsEnRoute = rowLoadsEnRoute.$$(".dropdown-menu-right li");
        dropDownLoadsEnRoute.findBy(exactText("Mark as delivered")).click();

        // Popup action
        String popupText = switchTo().alert().getText();
        assertThat(popupText).isEqualTo("Are you sure you want to Mark as delivered this load?");
        switchTo().alert().accept();

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Load Delivered successfully"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Вкладка Loads Delivered переводить вантаж в Loads Invoiced
        $$("#loadTabs .li-tabs-home").findBy(text("Loads Delivered")).click();
        SelenideElement activeTabLoadsDelivered = $("div.tab-pane.active");
        activeTabLoadsDelivered.$("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(atProNumber).pressEnter();

        // Вкладка Loads Delivered перевод груза в Loads Invoiced
        SelenideElement rowFindLoadsDelivered = activeTabLoadsDelivered.$$("table.table-striped tbody tr").get(0).shouldHave(text(atProNumber));
        rowFindLoadsDelivered.$("td a.view_load").shouldHave(text(atProNumber), EXPECT_5);
        rowFindLoadsDelivered.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        ElementsCollection dropDownLoadsInvoiced = rowFindLoadsDelivered.$$(".dropdown-menu-right li");
        rowFindLoadsDelivered.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        dropDownLoadsInvoiced.findBy(exactText("Mark as Invoiced")).click();

        // Modal Mark as invoiced
        $("#mark_as_invoiced").shouldBe(visible, EXPECT_10);
        $("#mark_as_invoiced_apply").shouldBe(visible, EXPECT_10).click();
        $("#mark_as_invoiced").shouldNotBe(visible, EXPECT_10);

        // Перевіряє відображання вантажу на Loads Invoiced
        $$("#loadTabs .li-tabs-home").findBy(text("Loads Invoiced")).click();
        SelenideElement activeTabLoadsInvoiced = $("div.tab-pane.active");
        SelenideElement rowFindLoadsInvoiced = activeTabLoadsInvoiced.$$("table.table-striped tbody tr").get(0).shouldHave(text(atProNumber));
        rowFindLoadsInvoiced.$("td a.view_load").shouldHave(text(atProNumber), EXPECT_5);

        // Sidebar переходить в Loads
        $(".loads-user").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".loads-user").click();
        $("body").click();

        // Sidebar Loads вводить Pro
        String firstRowText = $$("table.table-striped tbody tr").get(0).getText();
        boolean containsAtProNumber = firstRowText.contains(atProNumber);
        if (!containsAtProNumber){
            $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(atProNumber).pressEnter();
        }

        // Sidebar Loads. По номеру вантажу в меню вибирає Get invoice
        SelenideElement rowLoads = $$("table.table-striped tbody tr").get(0).shouldHave(text(atProNumber));
        rowLoads.$("td a.view_load").shouldHave(text(atProNumber), EXPECT_5);
        rowLoads.$("button.dropdown-toggle").shouldBe(enabled, EXPECT_GLOBAL).click();
        ElementsCollection dropDownLoads = rowLoads.$$(".dropdown-menu-right li");
        rowLoads.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        dropDownLoads.findBy(exactText("Get invoice")).click();

        // Перевіряє прев"ю
        $(".modal-view-pdf").shouldHave(text("Load invoice Trip#" + atProNumber));
        $(".invNum").shouldHave(text("Invoice #" + atProNumber));
        $(".pick").shouldHave(text("Kansas City, MO 64110"));
        $("td.drop span").shouldHave(text("New York, NY 10002"));

        // Завантажує документ на пк чекає завантаження 10 секунд
        DownloadDocument.document(atFileName);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
