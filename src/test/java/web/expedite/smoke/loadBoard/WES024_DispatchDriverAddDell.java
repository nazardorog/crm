package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.GlobalConfig;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES024_DispatchDriverAddDell {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 13. Dispatch/Add driver

    @Test
    public void driverAddDell() {

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

        // Create new load expedite
        String loadNumber = NewLoadExpedite.loadExpedite();

        final String atDriver1 = "Auto Test";
        final String atDriver2 = "AutoTest Driver2";
        final String atTeamDriver = "Auto Test2";
        final String atOwner1 = "Autotest 1 Owner";
        final String atOwner2 = "AutoTest 2 Owner";
        final String atTruck1 = "0303";
        final String atTruck2 = "0304";
        final String atAmount = "10000";

        // Перевіряє створений Load в таблиці
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoad.$("td a.view_load").shouldHave(text(loadNumber), EXPECT_5);

        // Клік кнопка Око
        rowLoad.$(".glyphicon-eye-open").click();
        $(".load-info-modal-dialog").shouldBe(visible, EXPECT_GLOBAL);

        // Frame Dispatch перевіряє дані Driver1
        SelenideElement rowDriver1 = $$(".table-modal-get-drivers tbody tr").findBy(text(atDriver1));
        rowDriver1.shouldHave(text(atDriver1));
        rowDriver1.shouldHave(text(atTeamDriver));
        rowDriver1.shouldHave(text(atOwner1));
        rowDriver1.shouldHave(text(atTruck1));
        rowDriver1.shouldHave(text("$800.00"));

        // Перевіряє Load Expenses
        ElementsCollection rate = $$(".broker-rate-block-flex");
        rate.get(0).shouldHave(text("$1,000.00"));
        rate.get(2).shouldHave(text("$800.00"));

        // Додає другого Driver
        $(".dispatch-viewing-border").click();
        $("#add_driver").shouldBe(visible, EXPECT_GLOBAL);

        // Driver
        $("#select2-load_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text(atDriver2)).click();
        $("#select2-load_driver_id-container").shouldHave(text(atDriver2));

        // Start Date
        $(".kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // Amount
        $("#loadexpenses-amount-disp").setValue(atAmount);
        $("#update_load_driver_send").click();

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Driver successfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє дані Driver2
        SelenideElement rowDriver2 = $$(".table-modal-get-drivers tbody tr").findBy(text(atDriver2));
        rowDriver2.shouldHave(text(atDriver2));
        rowDriver2.shouldHave(text(atOwner2));
        rowDriver2.shouldHave(text(atTruck2));
        rowDriver2.shouldHave(text("$100.00"));

        // Перевіряє Load Expenses після додавання Driver2
        rate.get(3).shouldHave(text("$900.00"));

        // Видаляє Driver2 перевіряє що видалений
        rowDriver2.$(".icon-close-dispatch-name").click();
        rowDriver2.shouldNotBe(visible, EXPECT_5);

        // Перевіряє Load Expenses після видалення водія
        rate.get(2).shouldHave(text("$800.00"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
