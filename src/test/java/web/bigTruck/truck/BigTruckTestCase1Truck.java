package web.bigTruck.truck;

import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class BigTruckTestCase1Truck {

    // Click Up:
    // CRM SEMI Truck
    // Trucks
    // 1. Создание New truck

    @Test
    public void newOwnersBigTruck() throws InterruptedException {

        System.out.println("BigTruckTestCase1Truck - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //переходить до списку Truck
        $(".owners-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".owners-user").click();
        $("body").click();

        System.out.println("BigTruckTestCase1Truck - Test Pass");
    }
}
