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

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(20));
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


        $$(".documents-envelopes .li a").findBy(exactText("Fast Signed")).shouldBe(visible);
        $$(".documents-envelopes .li a").findBy(exactText("Drafts")).shouldBe(visible);
        $$(".documents-envelopes .li a").findBy(exactText("Archive")).shouldBe(visible);
        $$(".documents-envelopes .li a").findBy(exactText("Starred")).shouldBe(visible);

        $$(".table-documents-content .style-filter-name a").findBy(exactText("Name of the document")).shouldBe(visible);
        $$(".table-documents-content .style-filter-name a").findBy(exactText("Type of Doc")).shouldBe(visible);
        $$(".table-documents-content .style-filter-name a").findBy(exactText("Status")).shouldBe(visible);
        $$(".table-documents-content .style-filter-name a").findBy(exactText("Updated At")).shouldBe(visible);

        System.out.println("TestCase11Interface - OK");
    }
}
