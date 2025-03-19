package Web;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;

public class Login {

    public String webSite = "https://preprod.empirenational.com/adm";

    @BeforeMethod
    public void loginWeb() throws InterruptedException {
        Configuration.browser = "chrome";
        System.setProperty("webdriver.chrome.driver", "C:/automation/chromedriver-win64/141/chromedriver.exe");
        Selenide.open(webSite);


        $("#loginform-username").setValue("test1te");
        $("#loginform-password").setValue("t34n2215P39L");
        $(".btn.btn-primary.btn-block.btn-flat").click();

        WebDriver driver = webdriver().driver().getWebDriver();
        driver.manage().window().maximize();

        String screenshotPath = Selenide.screenshot("screenTestEmpire");
        System.out.println("test passed");
        System.out.println("screenshot saved: " + screenshotPath);
        Thread.sleep(5000);
        System.out.println("loginWeb - OK");
    }

    @AfterMethod
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
