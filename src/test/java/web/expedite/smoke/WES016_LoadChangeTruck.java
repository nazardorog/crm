package web.expedite.smoke;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.LoginHelper;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.commonWeb.WebDriverConfig;
import utilsWeb.configWeb.GlobalConfig;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES016_LoadChangeTruck {
    // https://app.clickup.com/t/8698wrhrf
    // Замена одного трака на другой в активном грузе
    // Case 1 - замена через Dispatch
    // Case 2 - замена через Edit Dispatch

    @Test
    public void changeTruck () {
        
        // Login
        GlobalConfig.OPTION_LOGIN = "expedite_disp";
        WebDriverConfig.setup();
        LoginHelper.login();

        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        String pro_number = NewLoad.expedite();

        // Поиск груза
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(pro_number).pressEnter();
        $("button.view_load.btn.btn-xs").click();

        // Изменение трака через Dispatch
        $("a[title='Update Driver']").click();
        $("#add_driver").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-trucks-template-container").click();
        $("body span.select2-search--dropdown input").setValue("0304");
        $$("li.select2-results__option").findBy(Condition.text("0304")).click();

        // Remove help block
        boolean helpBlock = $(".help-block").shouldBe(visible, EXPECT_GLOBAL).isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }
        $("#update_load_driver_send").click();

        // Проверка наличия трака в Dispatch и на Load Board
        $("#loadDriversContent").shouldHave(text("0304"), EXPECT_GLOBAL);
        $("button.close").click();
        $$(".truck_trailer span").findBy(text("0304")).shouldBe(visible, EXPECT_10);
        
        // Изменение трака через Edit Dispatch
        $(".btn.dropdown-toggle.btn-xs").click();
        $$("a").findBy(Condition.text("Edit Dispatch")).click();
        $("[aria-labelledby='select2-load_truck_id-0-container']").click();
        $("body span.select2-search--dropdown input").setValue("0303");
        $$("li.select2-results__option").findBy(Condition.text("0303")).click();
        
        // Remove help block
        boolean helpBlock2 = $(".help-block").shouldBe(visible, EXPECT_GLOBAL).isDisplayed();
        if (helpBlock2){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        // Добавление в чат
        $("#loadexpenses-is_add_users_to_load_chat").shouldBe(visible, EXPECT_10).click();
        $("#loadexpenses-is_add_users_to_load_chat").shouldBe(Condition.selected);
        $("input[type='radio'][value='3']").shouldBe(visible).click();
        $("#dispatch_load_send").click();
        $("modal-content modal-dispatch-load-info").shouldNotBe(visible, EXPECT_GLOBAL);

        // Проверка наличия трака на Load Board
        $$(".truck_trailer span").findBy(text("0303")).shouldBe(visible, EXPECT_10);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
