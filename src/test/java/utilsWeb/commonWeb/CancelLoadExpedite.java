package utilsWeb.commonWeb;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


import utilsWeb.configWeb.GlobalLogin;

import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class CancelLoadExpedite {

    @Test
    public void cancelLoad () {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        int table = $$("#trucks_en_route table.table-striped tbody tr").size();
        int i = 0;

        while (table > 500 && i < 100) {

            SelenideElement row = $$("table.table-striped tbody tr").get(1);

            // Отримує номер вантажу
            String loadNumber = row.$("a.view_load").shouldBe(visible, EXPECT_GLOBAL).getAttribute("data-pro_number");
            System.out.println("Тест 1 номер вантажу: " + loadNumber);

            // Фільтрує по Pro вантажу
            $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();

            // Працює з першим рядком після фільтру по номеру вантажу
            SelenideElement rowFind = $$("table.table-striped tbody tr").get(0);
            rowFind.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
            rowFind.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);

            // Перевіряє в меню наявність пунктів
            SelenideElement canceledLoad = rowFind.$$(".dropdown-menu-right li").findBy(exactText("Canceled load"));
            SelenideElement approveCancel = rowFind.$$(".dropdown-menu-right li").findBy(text("Approve canceled load"));

            // Canceled load
            if (canceledLoad.exists()) {

                // Click в меню по Canceled load
                canceledLoad.shouldBe(clickable).click();
                $("#modal-canceled-load").shouldBe(visible, EXPECT_GLOBAL);
                $("#canceled-load-select-type").shouldHave(text("Through the fault of the broker/customer (no payments)"));
                $("#submit-canceled-load").shouldBe(visible, EXPECT_GLOBAL).click();

                // Toast message
                $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
                $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

                // Відкриває меню
                rowFind.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
                rowFind.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);

                // Перевіряє в меню наявність Approve canceled load
                approveCancel = rowFind.$$(".dropdown-menu-right li").findBy(exactText("Approve canceled load"));
            }

            // Approve canceled load
            if (approveCancel.exists()) {

                // Click в меню по Approve canceled load
                approveCancel.shouldBe(clickable).click();
                $("#approve_canceled_load").shouldBe(visible, EXPECT_GLOBAL);
                $("#approve_canceled_load_send").shouldBe(visible, EXPECT_GLOBAL).click();

                // Toast message
                $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
                $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Confirm the cancellation is successfully"));
                $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);
            }

            // Очищає Pro
            $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).clear();

            i++;
        }
    }
}
