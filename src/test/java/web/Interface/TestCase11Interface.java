package web.Interface;

import web.Login;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class TestCase11Interface extends Login {

        @Test
    public void docSignatureInterface() {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $(".reports-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".reports-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Documents signature"));
        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Documents signature")).shouldBe(visible);

        $("#new_fast_sign").shouldHave(text("Fast Sign"));
        $(".awaiting-signature-wrapper").shouldHave(text("Fast Signed"));
        $(".envelopes-block-name").shouldHave(text("Envelopes"));

        $$(".ul-menu-wrap .li a").findBy(exactText("Fast Signed"));
        $$(".documents-envelopes .li a").findBy(exactText("Drafts"));
        $$(".documents-envelopes .li a").findBy(exactText("Archive"));
        $$(".documents-envelopes .li a").findBy(exactText("Starred"));

        $$(".table-documents-content .style-filter-name a").findBy(exactText("Name of the document"));
        $$(".table-documents-content .style-filter-name a").findBy(exactText("Type of Doc"));
        $$(".table-documents-content .style-filter-name a").findBy(exactText("Status"));
        $$(".table-documents-content .style-filter-name a").findBy(exactText("Updated At"));

        System.out.println("TestCase11Interface - Test Pass");
    }
}
