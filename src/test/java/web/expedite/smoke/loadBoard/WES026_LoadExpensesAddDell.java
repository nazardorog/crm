package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoadExpedite;
import utilsWeb.configWeb.GlobalLogin;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES026_LoadExpensesAddDell {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 15. Dispatch/Add expenses

    @Test
    public void expensesAddDell() {

        // Login. Create new load expedite
        GlobalLogin.login("exp_disp1");
        String loadNumber = NewLoadExpedite.loadExpedite();

        // Data for test
        final String atName = "Expenses Name";
        final String atLocation = "Expenses Location";
        final String atAmount = "10000";

        // Перевіряє Load створено в таблиці
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowLoad.$("td a.view_load").shouldHave(text(loadNumber), EXPECT_5);

        // Клік кнопка Око
        rowLoad.$(".glyphicon-eye-open").click();
        $(".load-info-modal-dialog").shouldBe(visible, EXPECT_GLOBAL);

        // Додає Expenses
        $$(".dispatch-viewing-border").findBy(text("Expenses")).click();
        $("#add_expenses").shouldBe(visible, EXPECT_GLOBAL);

        // Вводить дані фрейм Add expenses
        $("#loadexpenses-name").setValue(atName);

        // Calendar Start Date
        $("#loadexpenses-start_date-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // Input Amount. Location
        $("#loadexpenses-amount-disp").setValue(atAmount);
        $("#loadexpenses-location").setValue(atLocation);

        // Submit frame Add expenses
        $("#update_load_expenses_send").click();
        $("#add_expenses").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Other expenses sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє Expenses додано на фрейм Dispatch
        $("#loadDriversContent table.table-modal-get-drivers").should(appear);
        SelenideElement expensesDispatch = $$("#loadDriversContent table.table-modal-get-drivers tbody tr").get(1);
        expensesDispatch.$$("td").findBy(text("Fees")).shouldBe(visible);
        expensesDispatch.$$("td").findBy(text("+$100.00")).shouldBe(visible);

        // Перевіряє суми Expenses фрейм Dispatch
        ElementsCollection expenses = $$(".broker-rate-block-flex");
        expenses.get(0).shouldHave(text("$1,000.00"));
        expenses.get(2).shouldHave(text("$800.00"));
        expenses.get(3).shouldHave(text("$900.00"));

        // Видаляє Expenses перевіряє що видалено
        expensesDispatch.$(".icon-close-dispatch-name").click();
        expensesDispatch.shouldNotBe(visible, EXPECT_5);

        // Перевіряє суми після видалення Expenses фрейм Dispatch
        expenses.get(2).shouldHave(text("$800.00"));
        expenses.get(3).$(".broker-rate-sum").shouldHave(exactText(""));

        // Закриває фрейм Dispatch
        $("#view_load button.close").shouldBe(visible, EXPECT_5).click();
        $("#view_load").shouldNotBe(visible, EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
