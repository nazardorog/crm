package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.*;

import utilsWeb.configWeb.GlobalLogin;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES025_DispatchWarehouseAddDell {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 14. Dispatch/Add warehouse

    @Test
    public void warehouseAddDell() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Create new load expedite
        String loadNumber = NewLoadExpedite.loadExpedite();

        // Data for test
        final String atWarehouses = "AutoTestWareHouses1";
        final String atLocation = "Location";
        final String atAmount = "10000";
        final String atContactPersonName = "contact AutoTestWareHouses1";

        // Перевіряє створений Load в таблиці
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoad.$("td a.view_load").shouldHave(text(loadNumber), EXPECT_5);

        // Клік кнопка Око
        rowLoad.$(".glyphicon-eye-open").click();
        $(".load-info-modal-dialog").shouldBe(visible, EXPECT_GLOBAL);

        // Додає Warehouse
        $$(".dispatch-viewing-border").findBy(text("Warehouses")).click();
        $("#add_warehouses").shouldBe(visible, EXPECT_GLOBAL);

        // Select Warehouses
        $("#select2-loadexpenses-warehouse_id-container").shouldBe(clickable).click();
        $(".select2-search__field").setValue(atWarehouses);
        $$(".select2-results__options").findBy(text(atWarehouses)).click();

        // Start Date
        $(".kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // Input Amount. Location
        $("#loadexpenses-amount-disp").setValue(atAmount);
        $("#loadexpenses-location").setValue(atLocation);

        // Submit frame Add warehouses
        $("#update_load_warehouses_send").click();

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Warehouse sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє Load Expenses фрейм Dispatch
        ElementsCollection expenses = $$(".broker-rate-block-flex");
        expenses.get(0).shouldHave(text("$1,000.00"));
        expenses.get(2).shouldHave(text("$800.00"));
        expenses.get(3).shouldHave(text("$900.00"));

        // Перевіряє Warehouse фрейм Dispatch
        SelenideElement rowWarehouse = $$(".table-modal-get-drivers tbody tr").findBy(text(atWarehouses));
        rowWarehouse.$(".view_warehouses").shouldHave(text(atWarehouses));
        rowWarehouse.$(".text-muted").shouldHave(text(atContactPersonName));
        rowWarehouse.shouldHave(text("+$100.00"));

        // Видаляє Warehouse перевіряє що видалений
        rowWarehouse.$(".icon-close-dispatch-name").click();
        rowWarehouse.shouldNotBe(visible, EXPECT_5);

        // Перевіряє після видалення Load Expenses
        expenses.get(2).shouldHave(text("$800.00"));
        expenses.get(3).$(".broker-rate-sum").shouldHave(exactText(""));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
