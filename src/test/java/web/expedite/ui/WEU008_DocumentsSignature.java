package web.expedite.ui;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;


import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WEU008_DocumentsSignature {

    @Test
    public void documentsSignature() {

        // Login
        GlobalLogin.login("exp_disp1");

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".reports-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".reports-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Documents signature"));
        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Documents signature")).shouldBe(visible);

        $("#new_fast_sign").shouldHave(text("Fast Sign"));
        $("a[href='/adm/signature-document/fast-signed']").shouldHave(text("Fast Signed"));
        $("a[href='/adm/signature-document/draft']").shouldHave(text("Drafts"));
        $("a[href='/adm/signature-document/archive']").shouldHave(text("Archive"));
        $("a[href='/adm/signature-document/starred']").shouldHave(text("Starred"));

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
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
