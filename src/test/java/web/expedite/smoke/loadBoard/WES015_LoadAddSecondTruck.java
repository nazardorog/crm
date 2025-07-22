package web.expedite.smoke.loadBoard;

import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.codeborne.selenide.Condition;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

@Listeners(utilsWeb.commonWeb.Listener.class)
@Epic("Expedite")
@Feature("Smoke")
public class WES015_LoadAddSecondTruck {
    // https://app.clickup.com/t/8698wg0cm
    // Добавление второго трака на груз

    @Test(description = "тест в description")
    @Story("Load board")
    @Description("дескріпш")
    @Severity(SeverityLevel.CRITICAL)
    public void addSecondTruck () {

        // Встановлюємо кастомну назву для тесту
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Добавление 2го трака на груз");
        });

        // Login
        GlobalLogin.login("exp_disp1");
        
        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        String proNumber = NewLoad.expedite();

        // Поиск груза
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();
        $("button.view_load.btn.btn-xs").click();

        // Dispatch добавление второго трака
        $(".updated-tabs-panel.active").shouldBe(visible, EXPECT_GLOBAL);
        $$("a[onclick^='showDriverModal']").findBy(text("Drivers")).click();
        $("#add_driver").shouldBe(visible, EXPECT_GLOBAL);
        $(".field-trucks-template .select2-selection").click();
        $("body span.select2-search--dropdown input").setValue("0304");
        $$("li.select2-results__option").findBy(Condition.text("0304")).click();

        // Remove help block
        boolean helpBlock = $(".help-block").shouldBe(visible, EXPECT_GLOBAL).isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        // Добавление в чат
        $("#loadexpenses-is_add_users_to_load_chat").shouldBe(visible, EXPECT_10).click();
        $("#loadexpenses-is_add_users_to_load_chat").shouldBe(Condition.selected);
        $("input[type='radio'][value='3']").shouldBe(visible).click();
        $("#update_load_driver_send").click();
        $("#add_driver").shouldNotBe(visible, EXPECT_GLOBAL);
        
        // Проверка наличия добавленного трака в Dispatch и на Load Board
        $("#loadDriversContent").shouldHave(text("0304"), EXPECT_GLOBAL);
        $("button.close").click();
        $$(".truck_trailer span").findBy(text("0304")).shouldBe(visible, EXPECT_10);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
