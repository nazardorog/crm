package web.expedite.full.loadBoard;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.CloseWebDriver;


import utilsWeb.configWeb.GlobalLogin;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WEF001_LoadLogin {
    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 1. Логин на веб

    @Test
    public void login() {

        // Login
        GlobalLogin.login("exp_disp1");

//        $(".content-header").shouldBe(visible, EXPECT_GLOBAL);
//        $(".content-header").shouldBe(text("Load Board"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
