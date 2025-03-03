package Web;

import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class TestCase11 {
    @Test(dependsOnMethods = {"Web.TestCase1.loginWeb"})
    public void DocumentsSignatureInterface() {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10));

        $(".reports-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".reports-user").click();
        $("body").click();

        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Documents signature"));
        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Documents signature")).shouldBe(visible);
    }
}
