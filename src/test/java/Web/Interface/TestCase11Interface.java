package Web.Interface;

import com.codeborne.selenide.CollectionCondition;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class TestCase11Interface{

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

        $$(".ul-menu-wrap li a").shouldHave(CollectionCondition.texts("Fast Signed", "Drafts", "Archive", "Starred"));
        $(".ul-menu-wrap li a").shouldHave(text("Drafts"));
        $(".ul-menu-wrap li a").shouldHave(text("Archive"));
        $(".ul-menu-wrap li a").shouldHave(text("Starred"));
    }
}
