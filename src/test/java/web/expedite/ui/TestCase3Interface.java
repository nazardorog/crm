package web.expedite.ui;

import web.Login;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class TestCase3Interface extends Login {

    @Test
    public void loadBoardInterface() {

        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();

        $("#new_load").shouldHave(text("New Load"));
        $("#OTR").shouldHave(text("OTR"));
        $(".content-wrapper").shouldHave(text("Load Board"));

        $(".button-set-language").shouldHave(text("EN"));
        $(".hidden-xs").shouldHave(text("Mary Miller"));
        $(".breadcrumb").shouldHave(text("Home"));
        $(".active").shouldHave(text("Load Board"));
        $("label").shouldHave(text("Show my loads"));

        $$("th").findBy(text("Pro")).shouldBe(visible);
        $$("th").findBy(text("Truck")).shouldBe(visible);
        $$("th").findBy(text("Driver / Carrier")).shouldBe(visible);
        $$("th").findBy(text("Customer/Broker")).shouldBe(visible);
        $$("th").findBy(text("Destination")).shouldBe(visible);
        $$("th").findBy(text("Actions")).shouldBe(visible);

        System.out.println("TestCase3Interface - Test Pass");
    }
}
