package Web.LoadBord;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class TestCase1LoadBoard {
    public String webSite = "https://preprod.empirenational.com/adm";

    @BeforeTest
    public void runBrowser() {
        Configuration.browser = "chrome";
        System.setProperty("webdriver.chrome.driver", "C:/automation/chromedriver-win64/141/chromedriver.exe");
        Selenide.open(webSite);
    }

    @Test
    public void loginWeb() throws InterruptedException {
        $("#loginform-username").setValue("test1te");
        $("#loginform-password").setValue("t34n2215P39L");
        $(".btn.btn-primary.btn-block.btn-flat").click();

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10));

        $("#new_load").click();

        $("#select2-broker_search-container").click();
        $(".select2-search__field").setValue("Auto test broker");
        $(".select2-results__options").shouldHave(text("LOADS DONE: 1")).click();

        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();
        $(".btn btn-default kv-datetime-picker").click();

        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $(".select2-results__options").shouldHave(text("Auto test shipper 2")).click();

        $("#loads-reference").setValue("1122334");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

//        $("#loads-pallets").setValue("1");
//        $("#loads-weight").setValue("100");
//        $(".btn.btn-primary.btn-block.btn-flat").click();

    }

    @AfterTest
    public void tearDown() {
        Selenide.closeWebDriver();
    }



}
