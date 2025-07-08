package web.expedite.smoke.offers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.checked;
import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.sleep;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;
import utilsWeb.commonWeb.Calendar;

public class WES051_FindTruckFilters {
    // https://app.clickup.com/t/86996m0dh
    // Поиск по ближайшим тракам + работа фильтров

    private static final String defaultTruckZip = "33133";
    private static final String canadaTruckZip = "G7N 0E5";

    private static final String driverName = "[sys]010_Owner Driver";
    private static final String truckNumber = "[sys]010_Truck";
    private static final String driverName1 = "[sys]011_Owner Driver";
    private static final String truckNumber1 = "[sys]011_Truck";
    private static final String driverName2 = "[sys]012_Owner Driver";
    private static final String truckNumber2 = "[sys]012_Truck";
    private static final String driverName3 = "[sys]013_Owner Driver";
    private static final String truckNumber3 = "[sys]013_Truck";
    private static final String driverName4 = "[sys]014_Owner Driver";
    private static final String truckNumber4 = "[sys]014_Truck";
    private static final String driverName5 = "[sys]015_Owner Driver";
    private static final String truckNumber5 = "[sys]015_Truck";
    private static final String driverName6 = "[sys]016_Owner Driver";
    private static final String truckNumber6 = "[sys]016_Truck";
    private static final String driverName7 = "[sys]017_Owner Driver";
    private static final String truckNumber7 = "[sys]017_Truck";
    private static final String driverName8 = "[sys]018_Owner Driver";
    private static final String truckNumber8 = "[sys]018_Truck";
    private static final String driverName9 = "[sys]019_Owner Driver";
    private static final String truckNumber9 = "[sys]019_Truck";

    @Test
    public void filters() {

        // Логин диспетчером
        GlobalLogin.login("exp_disp2");

        // Переход на вкладку Expedite Fleet
        $(".expedite-fleet-user").shouldBe(visible, EXPECT_GLOBAL).click();
        
        // Изменение локации и даты всех траков
        testTruckScenario(driverName, truckNumber, canadaTruckZip, "Available On");
        testTruckScenario(driverName1, truckNumber1, defaultTruckZip, "Available On");
        testTruckScenario(driverName2, truckNumber2, defaultTruckZip, "Available On");
        testTruckScenario(driverName3, truckNumber3, defaultTruckZip, "Not Available");
        testTruckScenario(driverName4, truckNumber4, defaultTruckZip, "Tomorrow");
        testTruckScenario(driverName5, truckNumber5, defaultTruckZip, "Yesterday");
        testTruckScenario(driverName6, truckNumber6, defaultTruckZip, "Available On");
        testTruckScenario(driverName7, truckNumber7, defaultTruckZip, "Available On");
        testTruckScenario(driverName8, truckNumber8, defaultTruckZip, "Available");
        testTruckScenario(driverName9, truckNumber9, defaultTruckZip, "Available On");

        // Переход на вкладку Offers
        $(".offers-user").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".offers-header__btn-wrapper").shouldBe(visible, EXPECT_GLOBAL).click();
        $(".modal-content").shouldBe(visible, EXPECT_GLOBAL);

        // Кейс 1 - Canada, "G7N 0E4"
        // Поиск траков в USA локации 33133
        $("input[name='location']").shouldBe(visible, EXPECT_GLOBAL).setValue(defaultTruckZip);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();
        $("div.empty").shouldBe(disappear, EXPECT_GLOBAL);
        
        // Проверка чтобы не было трака c локацией Canada в таблице 
        $(".popup-nearest-truck").shouldNotHave(text(truckNumber));

        // Выбор локации и CA в дропдауне
        $("input[name='location']").shouldBe(visible, EXPECT_GLOBAL).setValue("G7N 0E4");
        $("#country").selectOption("CA");
        $("#country").shouldHave(text("CA"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака в локации Canada
        $(".popup-nearest-truck").shouldHave(text(truckNumber));

        // Кейс 2 - USA, "33018"
        // Поиск траков в 33018
        $("input[name='location']").shouldBe(visible, EXPECT_GLOBAL).setValue("33018");
        $("#country").selectOption("US");
        $("#country").shouldHave(text("US"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия траков в USA локации и отсутствие трака Canada
        $(".popup-nearest-truck").shouldNotHave(text(truckNumber))
            .shouldHave(text(truckNumber1))
            .shouldHave(text(truckNumber2))
            .shouldHave(text(truckNumber6))
            .shouldHave(text(truckNumber7))
            .shouldHave(text(truckNumber8))
            .shouldHave(text(truckNumber9));

        // Кейс 3 - 900 miles, "23321"
        // Поиск траков с дефолтным фильтром и локацйией 23321 - 900 миль
        $("input[name='location']").shouldBe(visible, EXPECT_GLOBAL).setValue("23321");
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка отсутствия траков
        $("div.empty").shouldBe(visible, EXPECT_GLOBAL);

        // Выбор максимального радиуса поиска 900 миль
        $("#radius").shouldBe(visible).setValue("900");
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();
        $("div.empty").shouldBe(disappear, EXPECT_GLOBAL);

        // Проверка наличия траков с максимальным фильтром по радиусу
        $(".popup-nearest-truck").shouldHave(text(truckNumber1))
            .shouldHave(text(truckNumber2))
            .shouldHave(text(truckNumber6))
            .shouldHave(text(truckNumber7))
            .shouldHave(text(truckNumber8))
            .shouldHave(text(truckNumber9));

        // Кейс 4 - Not Available
        // Поиск траков с дефолтным фильтром и локацией 33018
        $("input[name='location']").shouldBe(visible, EXPECT_GLOBAL).setValue("33018");
        $("#radius").shouldBe(visible).setValue("200");
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка отсутствия трака со статусом Not Available
        $(".popup-nearest-truck").shouldHave(text(truckNumber1), EXPECT_GLOBAL)
            .shouldNotHave(text(truckNumber3));

        // Активация чекбокса Not Available
        $("#show_not_available").shouldBe(visible).click();
        $("#show_not_available").shouldBe(checked);
        sleep(1000); // без слипа нечего ждать чтобы заработал клик по кнопке
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака со статусом Not Available
        $(".popup-nearest-truck").shouldHave(text(truckNumber3), EXPECT_GLOBAL);

        // Кейс 5 - Tomorrow
        // Поиск траков с фильтром Today + Tomorrow
        $(".popup-nearest-truck").shouldNotHave(text(truckNumber4));
        $("#show_not_available").shouldBe(visible).click();
        $("#period_days").selectOption("Today + Tomorrow");
        $("#period_days").shouldHave(text("Today + Tomorrow"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака с завтрашней датой
        $(".popup-nearest-truck").shouldHave(text(truckNumber4), EXPECT_GLOBAL);

        // Кейс 6 - Yesterday
        // Поиск траков с фильтром All days
        $("#period_days").selectOption("All days");
        $("#period_days").shouldHave(text("All days"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака с вчерашней датой
        $(".popup-nearest-truck").shouldHave(text(truckNumber5), EXPECT_GLOBAL);

        // Кейс 7 - CV, Do not book for (Canada)
        // Поиск траков с фильром SV
        $("#trucks_type").selectOption("SV");
        $("#trucks_type").shouldHave(text("SV"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака с типом SV и отсутствием трака с CV
        $(".popup-nearest-truck").shouldHave(text(truckNumber1), EXPECT_GLOBAL)
            .shouldNotHave(text(truckNumber6));

        // Выбор фильтра CV
        $("#trucks_type").selectOption("CV");
        $("#trucks_type").shouldHave(text("CV"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака с типом CV, отсутствием трака с SV и наличием note "(Canada)"
        $$("a.view_driver").findBy(text(driverName6)).parent().$(".text-red").shouldHave(text(" (Canada)"), EXPECT_GLOBAL);
        $(".popup-nearest-truck").shouldHave(text(truckNumber6), EXPECT_GLOBAL)
            .shouldNotHave(text(truckNumber1));

        // Кейс 8 - SPA
        // Поиск траков с дефолтными значениями
        $("#trucks_type").selectOption("All Types");
        $("#trucks_type").shouldHave(text("All Types"), EXPECT_GLOBAL).shouldBe(enabled);
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия трака с Language "Only SPA"
        $$("a.view_driver").findBy(text(driverName7)).parent().$(".badge.label-warning").shouldHave(text("Only SPA"), EXPECT_GLOBAL);

        // Кейс 9 - Unreliable, Available
        // Проверка наличия трака в статусе Available и драйвера в статусе Unreliable
        $$("a.view_driver").findBy(text(driverName8)).closest("tr").shouldHave(attribute("class", "unreliable"));
        $(".truck-status-td").shouldHave(text("Available"));

        // Кейс 10 - TSA, US Citizen, PPE, No Canada
        // Поиск траков с Special Requirements "TSA"
        $(".select2-search__field").shouldBe(visible).click();
        $(".select2-search__field").shouldBe(visible).setValue("TSA");
        $$(".select2-results__option").findBy(text("TSA")).click();
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия траков с Special Requirements "TSA" и отсутствие без него
        $(".popup-nearest-truck").shouldHave(text(truckNumber7), EXPECT_GLOBAL)
            .shouldHave(text(truckNumber9))
            .shouldNotHave(text(truckNumber1))
            .shouldNotHave(text(truckNumber2))
            .shouldNotHave(text(truckNumber6))
            .shouldNotHave(text(truckNumber8));

        // Поиск траков с Special Requirements "TSA", "PPE", "US Citizen", "No Canada"
        $(".select2-search__field").shouldBe(visible).click();
        $(".select2-search__field").shouldBe(visible).setValue("PPE");
        $$(".select2-results__option").findBy(text("PPE")).click();
        $(".select2-search__field").shouldBe(visible).setValue("US Citizen");
        $$(".select2-results__option").findBy(text("US Citizen")).click();
        $(".select2-search__field").shouldBe(visible).setValue("No Canada");
        $$(".select2-results__option").findBy(text("No Canada")).click();
        $(".nearest-trucks-filter__apply-btn").shouldBe(visible).shouldBe(enabled).click();

        // Проверка наличия траков со всеми Special Requirements и отсутствие без них
        $(".popup-nearest-truck").shouldHave(text(truckNumber9), EXPECT_GLOBAL);
        $(".popup-nearest-truck").shouldNotHave(text(truckNumber1))
            .shouldNotHave(text(truckNumber2))
            .shouldNotHave(text(truckNumber6))
            .shouldNotHave(text(truckNumber7))
            .shouldNotHave(text(truckNumber8));
    }

    private void testTruckScenario(String driver, String truck, String zip, String status) {
        driverSearch(driver);
        changeTruckDate(truck, status);
        changeTruckLocation(zip, truck);
    }

    // Поиск драйвера по имени и проверка наличия в таблице
    private void driverSearch(String driverName) {
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible, EXPECT_GLOBAL).setValue(driverName).pressEnter();
        $(".driver-name").shouldHave(text(driverName), EXPECT_GLOBAL);
    }

    // Изменение даты и статуса трака
    private void changeTruckDate(String truckNumber, String status) {
        $(".available_update").shouldBe(visible).click();
        $("#trucks-form-update h4").shouldHave(text("Truck Number: " + truckNumber), EXPECT_GLOBAL);
        
        switch (status) {
            case "Available On":
                $("#trucks-status").selectOption("Available On");
                $(".kv-datetime-picker").shouldBe(enabled).click();
                $$("div.datetimepicker-days tfoot tr th").findBy(text("Today")).click();
                break;
            case "Available":
                $("#trucks-status").selectOption("Available");
                $("#update_location_date").click();
                $("#update_date_modal .modal-header h4").shouldHave(text("Is the driver`s location the same? Update only date to current date?"), EXPECT_GLOBAL);
                $("#update_location_date_send").click();
                break;
            case "Not Available":
                $("#trucks-status").selectOption("Not Available");
                $("#update_location_date").click();
                $("#update_date_modal .modal-header h4").shouldHave(text("Is the driver`s location the same? Update only date to current date?"), EXPECT_GLOBAL);
                $("#update_location_date_send").click();
                break;
            case "Tomorrow":
                $("#trucks-status").selectOption("Available On");
                $(".kv-datetime-picker").shouldBe(enabled).click();
                Calendar.setDateTime(1);
                break;
            case "Yesterday":
                $("#trucks-status").selectOption("Available On");
                $(".kv-datetime-picker").shouldBe(enabled).click();
                Calendar.setPreviousDateTime(2);
                break;
        }
    }

    // Изменение локации и проверка правильного города и адреса
    private void changeTruckLocation(String truckZip, String truckNumber) {
        String truckCity;
        String truckLocation;
        
        if (truckZip.equals(canadaTruckZip)) {
            truckCity = "Lac-Ministuk";
            truckLocation = "Lac-Ministuk, QC G7N0E5";
        } else {
            truckCity = "Coconut Grove";
            truckLocation = "Coconut Grove, FL 33133";
        }

        $("input[name='Trucks[last_zip]']").shouldBe(visible).setValue(truckZip);
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value(truckCity), EXPECT_GLOBAL);
        $("#update_truck_send").shouldBe(visible, EXPECT_5).click();
        $(".city-state-zip").shouldHave(text(truckLocation), EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
