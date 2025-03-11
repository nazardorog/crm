package Web.Interface;

import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TestCase10Interface{

    @Test(dependsOnMethods = {"Web.Login.loginWeb"})
    public void documentsSignatureInterface() {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10));

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

    }
}
