package web.Interface;

import web.Login;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TestCase7Interface extends Login {

        @Test
    public void shippersReceiversInterface() {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(20));

        $(".shippers-receivers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Shippers Receivers"));

        $(".shippers-receivers-index").shouldHave(text("Create Shippers Receivers"));
        $$("th").findBy(text("#")).shouldBe(visible);
        $$("th").findBy(text("Name")).shouldBe(visible);
        $$("th").findBy(text("location")).shouldBe(visible);
        $$("th").findBy(text("Street 1")).shouldBe(visible);
        $$("th").findBy(text("Street 2")).shouldBe(visible);
        $$("th").findBy(text("Lat")).shouldBe(visible);
        $$("th").findBy(text("Lng")).shouldBe(visible);

        $$("td").findBy(text("Semen Petrov")).shouldBe(visible);
        $$("td").findBy(text("Adjuntas, PR 00601")).shouldBe(visible);
        $$("td").findBy(text("14 AMBOY STq")).shouldBe(visible);
        $$("td").findBy(text("18.16")).shouldBe(visible);
        $$("td").findBy(text("-66.72")).shouldBe(visible);

        System.out.println("TestCase7Interface - OK");
    }
}
