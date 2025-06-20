package web.expedite.ui;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import com.codeborne.selenide.CollectionCondition;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WEU006_Warehouse {

    @Test
    public void warehouse() {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".warehouses-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".warehouses-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Warehouses"));
        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Warehouses")).shouldBe(visible);

        $(By.xpath("//div[input[@id='location']]//label")).shouldHave(text("Unit Location"));
        $(By.xpath("//div[input[@id='radius']]//label")).shouldHave(text("Search Radius (100-900 mi)"));
        $(By.xpath("//div[select[@id='number_warehouses']]//label")).shouldHave(text("Number warehouses"));
        $("#new_warehouses").shouldHave(text("Create Warehouses"));
        $(".view_map_warehouses").shouldHave(text("View map warehouses"));
        $("#radius").shouldBe(value("200"));
        $("#number_warehouses").shouldHave(value("25"));
        $("#number_warehouses").$$("option").shouldHave(CollectionCondition.texts("25", "35", "50", "75"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
