package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoadExpedite;
import utilsWeb.configWeb.GlobalLogin;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES034_LoadReportAdd {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 23. Добавление репорта на грузе

    @Test
    public void reportAdd() {

        // Login. Create new load expedite
        GlobalLogin.login("exp_disp1");
        String loadNumber = NewLoadExpedite.loadExpedite();

        // Переходить на вкладку Reports on Drivers
        $(".icon-reports-drivers").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".icon-reports-drivers").click();
        $("body").click();

        // Отримує коефіцієнт для зменшення балів в Report
        $(".dispatch-viewing-btn-name").shouldBe(clickable).click();
        $("#modal_categories").shouldBe(visible, EXPECT_GLOBAL);
        SelenideElement row = $$("#modal_categories tbody tr").findBy(text("Not following instructions"));
        String pointsReduction = row.$$("td").get(2).getText();
        String onlyNumber = pointsReduction.replaceAll("[^\\d-]", "");
        int points = Integer.parseInt(onlyNumber);
        $("#modal_categories .close").shouldBe(clickable).click();
        $("#modal_categories").shouldNotBe(visible, EXPECT_GLOBAL);

        // Click по кнопці око
        $(".logo-mini-icon").shouldBe(enabled, EXPECT_GLOBAL).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowFind = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        rowFind.$(".view_load").shouldBe(clickable).click();
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);

        // Перевіряє Score на фрейм Dispatch
        $("#pjaxTruckDriverRatingContent").shouldHave(text("#0303 Score : 10"));

        // Додає Report
        $(".report").click();
        $("#modal_add_report").shouldBe(visible, EXPECT_GLOBAL);

        // Вводить дані фрейм Add Report
        $("#select2-reportsdrivers-load_id-container").shouldHave(text(loadNumber));
        $("#trucks-template").shouldBe(clickable).click();
        $("#category").selectOption("Not following instructions");
        $("#sub-category").selectOption("Failed or refused to print documents");
        $("#reportsdrivers-report_note").setValue("Note1");

        // Download photo
        File file = new File(downloadsFolder + "4jpeg.jpg");
        $("input[data-name='photo-file-input']").uploadFile(file);

        // Submit фрейм Add Report
        $("#button_add_report").click();
        $("#modal_add_report").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Report created successfully"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє Score на фрейм Dispatch. Points з вкладки Reports on Drivers
        int score = 10 + points;
        $("#pjaxTruckDriverRatingContent").shouldHave(text("#0303 Score : " + score));

        // Перевіряє таблицю Check calls фрейм Dispatch
        $(".flex-reports-drivers-service").shouldBe(visible);
        $(".flex-reports-drivers-service .glyphicon-triangle-right").shouldBe(visible).click();
        $(".check_call_note").shouldHave(text("Not following instructions"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
