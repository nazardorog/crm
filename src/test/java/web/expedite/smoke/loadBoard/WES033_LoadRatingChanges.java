package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoadExpedite;
import utilsWeb.configWeb.GlobalLogin;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WES033_LoadRatingChanges {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 22. Изменение рейтинга водителя на грузе

    @Test
    public void ratingChanges() {

        // Login. Create new load expedite
        GlobalLogin.login("exp_disp1");
        String loadNumber = NewLoadExpedite.loadExpedite();

        // Click по кнопці око
        $(".logo-mini-icon").shouldBe(enabled, EXPECT_GLOBAL).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowFind = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowFind.$(".view_load").shouldBe(clickable).click();
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);

        // Перевіряє Score на фрейм Dispatch
        $("#pjaxTruckDriverRatingContent").shouldHave(text("#0303 Score : 10"));
        $("#pjaxTruckDriverRatingContent .score-block-show").click();

        // Фрейм Driver rating
        $("#modal_driver_rating").shouldBe(visible);

        // Ставить чек бокс Failed to print docs фрейм Driver rating
        $("#driverratingupdateform-categoryid-7").setSelected(true);

        // Отримує коефіцієнт для зменшення балів в Report
        String textScore = $x("//*[@id='form_update_driver_rating']/table/tbody/tr[4]/td[4]/div/div[1]/span[1]").getText();
        int points = Integer.parseInt(textScore);

        // Закриває фрейм Driver rating
        $(".button-modal-submit").click();
        $("#modal_driver_rating").shouldNotBe(visible);

        // Перевіряє Score на фрейм Dispatch. Points з вкладки Reports on Drivers
        int score = 10 - points;
        $("#pjaxTruckDriverRatingContent").shouldHave(text("#0303 Score : " + score));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
