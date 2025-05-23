package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;
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
    // 14. Dispatch/Add warehouse

    @Test
    public void expensesAddDell() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Create new load expedite
//        String loadNumber = NewLoadExpedite.loadExpedite();
        String loadNumber = "34726";

        // Data for test
        final String atName = "Name";
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

        // Додає Expenses
        int row = $$(".dispatch-viewing-border").size();
        System.out.println(row);
        $$(".dispatch-viewing-border").findBy(text("Expenses")).click();
        $("#add_expenses").shouldBe(visible, EXPECT_GLOBAL);
    }
}
