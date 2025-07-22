package web.expedite.smoke.loadBoard;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES021_LoadBounceToEnRoute {
    // https://app.clickup.com/t/86991ey9h
    // Перевод обратно из Deliverd в En route

    @Test
    public void bounceToEnRoute () {

        // Login
        GlobalLogin.login("exp_disp1");

        String proNumber = NewLoad.expedite();

        // Выход из аккаунта
        $(".user-image-profile").click();
        $(".exit-user-block").shouldBe(visible).click();

        // Логин под трекером
        GlobalLogin.login("exp_tracker1");

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

        // Закриває модальне вікно Load Delivered for Trip#
        $("#load_delivered").shouldBe(visible, EXPECT_GLOBAL);
        $("#load_delivered .close").click();
        $("#load_delivered").shouldNotBe(visible, EXPECT_GLOBAL);

        // Перевод груза в Loads Delivered
        $$("tbody tr").findBy(text("Load Delivered")).shouldBe(visible);
        $(".btn.dropdown-toggle.btn-xs").shouldBe(visible, EXPECT_5).click();
        $(".mark_delivered").shouldBe(visible, EXPECT_5).click();

        // Подтверждение перевода груза в Loads Delivered
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Are you sure you want to Mark as delivered this load?");
        switchTo().alert().accept();

        // Переход в Loads Delivered
        $(".li-tabs-home.li-tabs-delivered.tab-next-li").shouldBe(visible).click();

        // Проверка наличия груза в Loads Delivered
        $("td.our_pro_number").shouldHave(text(proNumber), EXPECT_GLOBAL);

        // Перевод трака в Loads en Route
        $(".btn-group").shouldBe(visible).click();
        $(".return_status_en_route").shouldBe(visible).click();
        $("#loadnotes-note").shouldBe(visible, EXPECT_5).setValue("Bounce to en route");
        $("#return_en_route_send").click();

        // Переход на вкладку Loads en Route и проверка наличия груза
        $(".li-tabs-home.li-tabs-route").shouldBe(visible).click();
        $("td.our_pro_number").shouldBe(visible, EXPECT_5).shouldHave(text(proNumber));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
