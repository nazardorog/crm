package web.expedite.ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WEU003_Truck {

    @Test
    public void truck() {

        // Login
        GlobalLogin.login("exp_disp1");

        String runEnv = System.getenv("RUN_ENV");
        System.out.println("RUN_ENV = " + runEnv);
        System.out.println("Allure reports will be saved to: " + Configuration.reportsFolder);

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".trucks-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".trucks-user").click();
        $("body").click();
        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Trucks"));

        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Trucks")).shouldBe(visible);

        $(".col-md-2.trucks-head-block-search .control-label").shouldHave(text("Truck Number Length"));
//        $(".form-group field-truckssearch-type_truck").shouldHave(text("Type Truck")); //не бачить елемент

        $$("th").findBy(text("Unit")).shouldBe(visible);
        $$("th").findBy(text("Vin")).shouldBe(visible);
        $$("th").findBy(text("Plate")).shouldBe(visible);
        $$("th").findBy(text("Make")).shouldBe(visible);
        $$("th").findBy(text("Model")).shouldBe(visible);
        $$("th").findBy(text("Status")).shouldBe(visible);
        $$("th").findBy(text("Date When Will Be There")).shouldBe(visible);
        $$("th").findBy(text("Type Truck")).shouldBe(visible);
        $$("th").findBy(text("Year")).shouldBe(visible);
        $$("th").findBy(text("Driver Support")).shouldBe(visible);
        $$("th").findBy(text("Hr Content")).shouldBe(visible);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
