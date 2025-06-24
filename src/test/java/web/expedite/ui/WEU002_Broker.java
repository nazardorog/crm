package web.expedite.ui;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;

import org.testng.annotations.Test;
import utilsWeb.configWeb.GlobalLogin;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WEU002_Broker {

    @Test
    public void broker() {

        System.out.println("Давай наярівай");
        String runEnv = System.getenv("RUN_ENV");
        System.out.println("RUN_ENV = " + runEnv);

        // Login
        GlobalLogin.login("exp_disp1");

        System.out.println("RUN_ENV = " + runEnv);
        System.out.println("Allure reports will be saved 2: " + Configuration.reportsFolder);

        // Створюємо папку, якщо не існує
        new File("target/allure-results").mkdirs();

        // Налаштовуємо Allure
        System.setProperty("allure.results.directory", "target/allure-results");
        Configuration.reportsFolder = "target/allure-results";

        System.out.println("Allure results будуть збережені в: " +
                new File("target/allure-results").getAbsolutePath());

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();;
        System.out.println("Половинка тесту юхуу");
        $("#OTR").shouldHave(text("OTR"));
        $(".content-header").shouldHave(text("Brokers"));

        $$(".breadcrumb li").findBy(text("Home")).shouldBe(visible);
        $$(".breadcrumb li").findBy(text("Brokers")).shouldBe(visible);

        System.out.println("Allure report path: " + new File(Configuration.reportsFolder).getAbsolutePath());
        System.out.println("Прилетіли");
        System.out.println("=== ДІАГНОСТИКА ALLURE ===");
        System.out.println("Configuration.reportsFolder: " + Configuration.reportsFolder);
        System.out.println("System property allure.results.directory: " +
                System.getProperty("allure.results.directory"));

        File targetAllure = new File("target/allure-results");
        File rootAllure = new File("allure-results");

        System.out.println("target/allure-results існує: " + targetAllure.exists());
        System.out.println("allure-results існує: " + rootAllure.exists());

        if (targetAllure.exists()) {
            System.out.println("Файли в target/allure-results: " +
                    Arrays.toString(targetAllure.list()));
        }
        if (rootAllure.exists()) {
            System.out.println("Файли в allure-results: " +
                    Arrays.toString(rootAllure.list()));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
