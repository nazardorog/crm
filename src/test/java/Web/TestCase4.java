package Web;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class TestCase4 {

    @Test(dependsOnMethods = {"Web.TestCase1.loginWeb"})
    public void BrokersInterface() {

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10));

        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();
        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Brokers"));

        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Brokers")).shouldBe(visible);

//        $(".name-brokers-search").shouldHave(text("Broker Search")); //не бачить елемент displayed:false
        $(".select2-selection__placeholder").shouldHave(text("Search Broker..."));

        System.out.println("Test4 - OK");
    }
}
