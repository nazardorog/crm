package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import java.io.IOException;

public class MAD010_TaskBar {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void loginMobile() throws IOException {

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // Tab My profile
        WebElement moreElement = driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));
        Assert.assertTrue(moreElement.isDisplayed(), "Елемент My profile не відображається");
        moreElement.click();

        // Tab My profile. Profile Information
        driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Profile Information\"]"));

        // Tab My profile. Active Load
        driver.findElement(By.xpath("//android.widget.Button[@content-desc=\"Profile Information\"]"));

        // Tab My profile. Support
        driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Support\"]"));

        // Tab My profile. Set resting time
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Set resting time\")"));

        // Tab My profile. Status Driver
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Available On')]"));

        // Tab Offers & Loads
        WebElement offersLoads = driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Offers & Loads') and contains(@content-desc, 'Tab 2 of 3')]"));
        Assert.assertTrue(moreElement.isDisplayed(), "Елемент Offers & Loads не відображається");
        offersLoads.click();

        // Tab Offers & Loads. Offers. Перевіряє Booked Offers, Accepted bids, New load offers, Offers Archive
        driver.findElement(By.xpath("//android.view.View[contains(@content-desc, 'Offers')]")).click();
        driver.findElement(AppiumBy.accessibilityId("Booked Offers"));
        driver.findElement(AppiumBy.accessibilityId("Accepted bids"));
        driver.findElement(AppiumBy.accessibilityId("New load offers"));
        driver.findElement(AppiumBy.accessibilityId("Offers Archive"));

        // Tab Offers & Loads. My loads.
        driver.findElement(By.xpath("//android.view.View[contains(@content-desc, 'My Loads')]")).click();
        driver.findElement(AppiumBy.accessibilityId("Active Loads"));
        driver.findElement(AppiumBy.accessibilityId("Archive Loads"));

        // Button More. Tutorial,Settings, Contact Us, Password change, Log Out
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'More')]")).click();
        driver.findElement(AppiumBy.accessibilityId("Tutorial"));
        driver.findElement(AppiumBy.accessibilityId("Settings"));
        driver.findElement(AppiumBy.accessibilityId("Contact Us"));
        driver.findElement(AppiumBy.accessibilityId("Password change"));
        driver.findElement(AppiumBy.accessibilityId("Rules and regulations"));
        driver.findElement(AppiumBy.accessibilityId("Log Out"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
