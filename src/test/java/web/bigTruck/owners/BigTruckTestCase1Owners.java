package web.bigTruck.owners;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class BigTruckTestCase1Owners {

    // Click Up:
    // CRM SEMI Truck
    // Owners
    // 1. Создание owner (type company )

    @Test
    public void newOwnersBigTruck() throws InterruptedException{

        System.out.println("BigTruckTestCase1Owners - Start");
        
        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        System.out.println("Tear down - close WebDriver");
        web.config.CloseWebDriver.tearDown();
    }
}
