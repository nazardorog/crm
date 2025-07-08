package web.expedite.smoke.offers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.api.driver.apiAcceptOffer;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES053_OfferHold {
    //https://app.clickup.com/t/86996m0f3
    //Hold, Renew, Delete

    @Test
    public void offerHold() {

        // Драйвер
        String driver = "ext_driver2";
        String driverName = "Autotest Driver2";

        // Трак
        String truckNumber = "0304";
        String truckZip = "97210";
        String truckCity = "Portland";
        String truckLocation = "Portland, OR 97210";

        // Оффер
        String puLocation = "97019";
        String puDate = "Offer hold test";
        String delLocation = "97411";
        String delDate = "TOMORROW";
        String miles = "800";
        String weight = "1400";
        String pallets = "1";
        String dims = "100x100x100";

        // Логин диспетчером 2
        GlobalLogin.login("exp_disp2");
        
        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);
        
        // Поиск драйвера на Expedite fleet
        $(".expedite-fleet-user").shouldBe(visible, EXPECT_GLOBAL).click();
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible, EXPECT_GLOBAL).setValue(driverName).pressEnter();
        $(".driver-name").shouldHave(text(driverName), EXPECT_GLOBAL);
        
        // Изменение локации трака
        $(".available_update").shouldBe(visible).click();
        $("#trucks-form-update h4").shouldHave(text("Truck Number: " + truckNumber), EXPECT_GLOBAL);
        $("input[name='Trucks[last_zip]']").shouldBe(visible).setValue(truckZip);
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value(truckCity), EXPECT_GLOBAL);
        
        // Изменение статуса и даты тарка
        $("#trucks-status").selectOption("Available On");
        $(".kv-datetime-picker").shouldBe(enabled).click();
        $$("div.datetimepicker-days tfoot tr th").findBy(text("Today")).click();
        $("#update_truck_send").shouldBe(visible, EXPECT_5).click();
        
        // Проверка измененной локации
        $(".city-state-zip").shouldHave(text(truckLocation), EXPECT_GLOBAL);

        // Создание оффера
        createOffer(truckNumber, truckZip, truckCity, truckLocation, 
            puLocation, puDate, delLocation, delDate, 
            miles, weight, pallets, dims);
        
        // Открытие оффер чата
        $(".accordion", 0).click();
        $(".truck-info").shouldBe(visible).click();

        // Проверка что кнопка поставить на холд не видна
        $("#place-driver-on-hold").shouldNotBe(visible, EXPECT_GLOBAL);

        // Получение offer-truck-id
        String offerTruckId = $(".body-truck").shouldBe(visible).getAttribute("id");
        offerTruckId = offerTruckId != null ? offerTruckId.replace("offer-truck-id-", "") : "";

        // Принятие оффера драйвером
        apiAcceptOffer.acceptOffer(driver, offerTruckId);

        // Проверка что драйвер принял оффер
        $(".chat-system-message", 1).shouldBe(visible, EXPECT_GLOBAL).shouldHave(text(driverName + " accepted your offer."));

        // Выход из аккаунта
        $(".user-image-profile").click();
        $(".exit-user-block").shouldBe(visible).click();

        // Логин диспетчером 1
        GlobalLogin.login("exp_disp1");

        // Создание оффера
        createOffer(truckNumber, truckZip, truckCity, truckLocation, 
            puLocation, puDate, delLocation, delDate, 
            miles, weight, pallets, dims);

        // Открытие оффер чата
        $(".accordion", 0).click();
        $(".truck-info").shouldBe(visible).click();

        // Постановка на холд
        $$("button").findBy(text("Place driver on Hold")).shouldBe(visible, EXPECT_GLOBAL).click();

        // Проверка что кнопки в оффер чате видно
        $("#driver-renew-hold").shouldBe(visible, EXPECT_GLOBAL);
        $$("button").findBy(text("Reject the driver")).shouldBe(visible);
        $$("button").findBy(text("Book driver on this load")).shouldBe(visible);
        
        // Продление холда и проверка таймера
        $$("button").findBy(text("Renew Hold")).shouldBe(visible).click();
        $(".driver-renew-hold-count-title").shouldHave(text("hold 15 mins by "));

        // Выход из аккаунта
        $(".user-image-profile").click();
        $(".exit-user-block").shouldBe(visible).click();

        // Логин диспетчером 2
        GlobalLogin.login("exp_disp2");

        // Переход на вкладку Offers
        $(".offers-user").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".offers-header__btn-wrapper").shouldBe(visible, EXPECT_GLOBAL);

        // Открытие оффер чата
        $(".accordion", 0).click();
        $(".truck-info").shouldBe(visible).click();

        // Проверка что трак на холде другого диспетчера
        $(".p-driver-on-hold").shouldHave(text("This driver is currently on hold"), EXPECT_GLOBAL);

        // Перевод оффера в архив
        $(".offer-close").shouldBe(visible).click();

        // Подтверждение
        String popapText1 = switchTo().alert().getText();
        assertThat(popapText1).isEqualTo("Are you sure you want to delete this offer?");
        switchTo().alert().accept();

        // Выход из аккаунта
        $(".user-image-profile").click();
        $(".exit-user-block").shouldBe(visible).click();

        // Логин диспетчером 1
        GlobalLogin.login("exp_disp1");

        // Переход на вкладку Offers
        $(".offers-user").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".offers-header__btn-wrapper").shouldBe(visible, EXPECT_GLOBAL);

        // Открытие оффер чата
        $(".accordion", 0).click();
        $(".truck-info").shouldBe(visible).click();

        // Удаление холда
        $$("button").findBy(text("Delete Hold")).shouldBe(visible).click();

        // Перевод оффера в архив
        $(".offer-close").shouldBe(visible).click();

        // Подтверждение
        String popapText2 = switchTo().alert().getText();
        assertThat(popapText2).isEqualTo("Are you sure you want to delete this offer?");
        switchTo().alert().accept();
    }

    private void createOffer(String truckNumber, String truckZip, 
        String truckCity, String truckLocation,
        String puLocation, String puDate, String delLocation, 
        String delDate, String miles, String weight, 
        String pallets, String dims) {

        // Переход на вкладку Offers
        $(".offers-user").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".offers-header__btn-wrapper").shouldBe(visible, EXPECT_GLOBAL).click();
        
        // Поиск трака в модалке New offer
        $(".modal-content").shouldBe(visible, EXPECT_GLOBAL);
        $("input[name='location']").shouldBe(visible, EXPECT_GLOBAL).setValue(truckZip);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).click();
        $("div.empty").shouldBe(disappear, EXPECT_GLOBAL);
        $("input[name='check_all_for_offer']").click();
        $("input[name='check_all_for_offer']").shouldBe(checked);
        $("a[title='Make offer']").shouldBe(visible).click();
        
        // Заполнение полей оффера в модалке Make offer
        $("#view_make_offer2 .modal-title").shouldHave(text("Make offer"), EXPECT_10);
        $("input[name='ShipperOrigin[0][location]']").shouldBe(visible).setValue(puLocation);
        $("input[name='ShipperDestination[0][location]']").shouldBe(visible).setValue(delLocation);
        $("input[name='ShipperOrigin[0][date]']").shouldBe(visible).setValue(puDate);
        $("input[name='ShipperDestination[0][date]']").shouldBe(visible).setValue(delDate);
        $("input[name='OfferForm[miles]']").shouldBe(visible).setValue(miles);
        $("input[name='OfferForm[weight]']").shouldBe(visible).setValue(weight);
        $("input[name='OfferForm[pallets]']").shouldBe(visible).setValue(pallets);
        $("input[name='OfferForm[dimensions]']").shouldBe(visible).setValue(dims);

        // Создание оффера
        $("#send-offer-form").shouldBe(visible).click();
        $(".close").shouldBe(visible).click();
        $("#view_make_offer2 .modal-title").shouldNotBe(visible, EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}