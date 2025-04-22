package web.expedite.ui;

import web.Login;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TestCase4Interface extends Login {

        @Test
    public void brokersInterface() {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();
        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Brokers"));

        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Brokers")).shouldBe(visible);

        System.out.println("TestCase4Interface - Test Pass");
    }
}
