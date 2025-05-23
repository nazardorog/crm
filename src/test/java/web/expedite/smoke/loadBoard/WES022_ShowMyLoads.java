package web.expedite.smoke.loadBoard;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES022_ShowMyLoads {
    // https://app.clickup.com/t/86991ezdv
    // Отображение моих грузов Show my Loads

    @Test
    public void bounceToEnRoute () {

        // Логин диспетчером
        GlobalLogin.login("exp_disp2");

        String proNumber = NewLoad.expedite();

        // Выход из аккаунта
        $(".user-image-profile").shouldBe(visible, EXPECT_10).click();
        $(".exit-user-block").shouldBe(visible).click();

        // Логин диспетчером тимлидом
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        // Проверка наличия груза без Show my loads
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();
        $("td.our_pro_number").shouldHave(text(proNumber), EXPECT_GLOBAL);

        // Проверка наличия груза c Show my loads
        $("#show_my_loads").shouldBe(visible, EXPECT_GLOBAL).click();
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();
        $("tbody").shouldHave(text("No results found"), EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
