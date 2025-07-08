package web.expedite.smoke.offers;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.api.driver.apiOffers;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES052_OfferCreate {
    // https://app.clickup.com/t/86996m0e5
    // Успешное создание оффера

    @Test
    public void offerCreate() {

        // Драйвер
        String driver = "ext_driver1";
        String driverName = "auto test";

        // Трак
        String truckNumber = "0303";
        String truckZip = "10002";
        String truckCity = "New York";
        String truckLocation = "New York, NY 10002";

        // Оффер
        String puLocation = "10007";
        String puDate = "Create offer test";
        String delLocation = "12345";
        String delDate = "TOMORROW";
        String miles = "1234";
        String weight = "1000";
        String pallets = "1";
        String dims = "100x100x100";
        String offerMessage = "New offer for you!";

        // Логин диспетчером
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

        // Проверка pu и del созданного оффера
        $(".origin-title span").shouldBe(visible).shouldHave(text("PU1 : " + puLocation + " / " + puDate), EXPECT_GLOBAL);
        $(".destination-title span").shouldBe(visible).shouldHave(text("DEL: " + delLocation + " / " + delDate), EXPECT_GLOBAL);

        // Открытие оффер чата
        $(".accordion").click();
        $(".truck-info").shouldBe(visible).click();

        // Проверка трака и деталей оффера
        $(".truck-number-title").shouldBe(visible, EXPECT_GLOBAL).shouldHave(text(truckNumber));
        $(".chat-main-location").shouldHave(text(truckLocation));
        $(".ul-offer-details", 0).shouldBe(visible).shouldHave(text("Loaded miles: " + miles));
        $(".ul-offer-details", 0).shouldBe(visible).shouldHave(text("Pallets/Pieces: " + pallets));
        $(".ul-offer-details", 1).shouldBe(visible).shouldHave(text("Weight, lbs: " + weight));
        $(".ul-offer-details", 1).shouldBe(visible).shouldHave(text("DIMS, in: " + dims));
        $(".chat-system-message").shouldBe(visible).shouldHave(text(offerMessage));

        // OfferId на crm
        String OfferId = $(".body-truck").getAttribute("data-offer-id");

        // Получение данных с апи
        String[] apiOfferData = apiOffers.offers(driver);
        String apiOfferId = apiOfferData[0];
        String apiStatus = apiOfferData[1];
        String apiPuLocation = apiOfferData[2];
        String apiPuDate = apiOfferData[3];
        String apiDelLocation = apiOfferData[4];
        String apiDelDate = apiOfferData[5];
        String apiTruckNumber = apiOfferData[6];
        String apiMiles = apiOfferData[7];
        String apiWeight = apiOfferData[8];
        String apiPallets = apiOfferData[9];
        String apiDims = apiOfferData[10];
        String apiLastMessage = apiOfferData[11];

        // Сравнение данных с crm и api
        assertThat(OfferId).isEqualTo(apiOfferId);
        assertThat("1").isEqualTo(apiStatus);
        assertThat(puLocation).isEqualTo(apiPuLocation);
        assertThat(puDate).isEqualTo(apiPuDate);
        assertThat(delLocation).isEqualTo(apiDelLocation);
        assertThat(delDate).isEqualTo(apiDelDate);
        assertThat(truckNumber).isEqualTo(apiTruckNumber);
        assertThat(miles).isEqualTo(apiMiles);
        assertThat(weight).isEqualTo(apiWeight);
        assertThat(pallets).isEqualTo(apiPallets);
        assertThat(dims).isEqualTo(apiDims);
        assertThat(offerMessage).isEqualTo(apiLastMessage);

        // Перевод оффера в архив
        $(".offer-close").shouldBe(visible).click();

        // Подтверждение
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Are you sure you want to delete this offer?");
        switchTo().alert().accept();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
