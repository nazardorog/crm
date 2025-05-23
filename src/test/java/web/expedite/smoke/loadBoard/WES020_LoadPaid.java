package web.expedite.smoke.loadBoard;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.switchTo;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES020_LoadPaid {
    // https://app.clickup.com/t/86991ex3a
    // Перевод груза с load invoicеd в loads paid

    @Test
    public void loadPaid () {

        // Login
        GlobalLogin.login("exp_disp1");

        String proNumber = NewLoad.expedite();

        // Выход из аккаунта
        $(".user-image-profile").click();
        $(".exit-user-block").shouldBe(visible).click();

        // Логин под аккаунтингом
        GlobalLogin.login("exp_accounting1");

        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        // Поиск груза
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();
        $("td.our_pro_number").shouldHave(text(proNumber), EXPECT_GLOBAL);

        // Переход в Delivery Location
        $("a.view_delivery_location.default-location-status").shouldBe(visible, EXPECT_10).click();
        $("#view_item .loads-delivery-view").shouldBe(visible, EXPECT_5);

        // Установка даты доставки
        $("#loadsdeliverylocations-date_delivery-datetime .kv-datetime-picker").shouldBe(enabled).click();
        $$("div.datetimepicker-days tfoot tr th").findBy(text("Today")).click();
        $("#view_item .modal-header button.close").click();

        // Перевод груза в Loads Delivered
        $$("tbody tr").findBy(text("Load Delivered")).shouldBe(visible, EXPECT_5);
        $(".btn.dropdown-toggle.btn-xs").shouldBe(visible, EXPECT_5).click();
        $(".dropdown-menu.dropdown-menu-right").shouldBe(visible, EXPECT_5);
        $("a.mark_delivered").shouldBe(visible).click();

        // Подтверждение перевода груза в Loads Delivered
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Are you sure you want to Mark as delivered this load?");
        switchTo().alert().accept();

        // Переход в Loads Delivered
        $(".li-tabs-home.li-tabs-delivered.tab-next-li").shouldBe(visible, EXPECT_GLOBAL).click();

        // Проверка наличия груза в Loads Delivered
        $("td.our_pro_number").shouldHave(text(proNumber), EXPECT_10);

        // Перевод груза в Loads Invoiced
        $(".btn.dropdown-toggle.btn-xs").shouldBe(visible, EXPECT_5).click();
        $(".dropdown-menu.dropdown-menu-right").shouldBe(visible, EXPECT_5);
        $("a.mark_invoiced").shouldBe(visible, EXPECT_5).click();
        $("#mark_as_invoiced_apply").shouldBe(visible, EXPECT_10).click();
        $(".modal-dialog.modal-lg.modal-approve-canceled-load").shouldNotBe(visible, EXPECT_GLOBAL);

        // Переход в Loads Invoiced и поиск груза
        $(".li-tabs-home.li-tabs-invoiced").shouldBe(visible).click();
        $("#invoice-loads-grid-filters input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();

        // Чекбокс Load paid
        $("input.container-checkbox-green[value='" + proNumber + "']").shouldBe(visible, EXPECT_GLOBAL);
        executeJavaScript("arguments[0].checked = true;", $("input.container-checkbox-green[value='" + proNumber + "']"));
        $("#paid-bulk-btn").shouldBe(visible).click();

        // Переход на вкладку Paid
        $(".li-tabs-home.li-tabs-paid.tab-next-li").shouldBe(visible).click();
        $(".li-tabs-home.li-tabs-paid.active").shouldBe(visible);

        // Переход в Loads Paid и поиск груза
        $("#paid-loads-grid td.our_pro_number").shouldBe(visible, EXPECT_5).shouldHave(text(proNumber));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
