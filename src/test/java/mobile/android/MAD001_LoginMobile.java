package mobile.android;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsMobile.configMobile.*;

import java.io.IOException;

public class MAD001_LoginMobile {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void loginMobile() throws IOException {

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // More
        WebElement element = driver.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
        element.click();

        // Log Out
        WebElement logoutButton = driver.findElement(By.xpath("//android.view.View[@content-desc='Log Out']"));
        logoutButton.click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
