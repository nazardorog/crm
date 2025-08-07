package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsMobile.configMobile.GlobalLoginMob;

import java.time.Duration;

public class MAD005_Tutorial {

    GlobalLoginMob openApp = new GlobalLoginMob();
    Integer numberVideo;

    @Test
    public void Tutorial() throws Exception {

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // More
        WebElement moreElement = driver.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
        Assert.assertTrue(moreElement.isDisplayed(), "Елемент 'More Tab 3 of 3' не відображається");
        moreElement.click();

        // More Tutorial
        WebElement logoutButton = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Tutorial']"));
        logoutButton.click();

        // Відкриває відео How to update location?
        numberVideo = 1;
        WebElement updateLocation = driver.findElement(AppiumBy.accessibilityId("How to update location?"));
        updateLocation.click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
        checkVideo(numberVideo);
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();

        // Відкриває відео How to change your status?
        numberVideo = 2;
        WebElement changeStatus = driver.findElement(AppiumBy.accessibilityId("How to change your status?"));
        changeStatus.click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
        checkVideo(numberVideo);
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();

        // Відкриває відео How to set the resting time?
        numberVideo = 3;
        WebElement restingTime = driver.findElement(AppiumBy.accessibilityId("How to set the resting time?"));
        restingTime.click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
        checkVideo(numberVideo);
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();

        // Відкриває відео How to accept the load offer?
        numberVideo = 4;
        WebElement acceptLoadOffer = driver.findElement(AppiumBy.accessibilityId("How to accept the load offer?"));
        acceptLoadOffer.click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
        checkVideo(numberVideo);
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();

        // Відкриває відео How to reject a load offer?
        numberVideo = 5;
        WebElement rejectLoadOffer = driver.findElement(AppiumBy.accessibilityId("How to reject a load offer?"));
        rejectLoadOffer.click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
        checkVideo(numberVideo);
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();

        // Відкриває відео How to check load history? Loads&Offers in one tab.
        numberVideo = 6;
        WebElement loadsOffers = driver.findElement(AppiumBy.accessibilityId("How to check load history? Loads&Offers in one tab."));
        loadsOffers.click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"How to check load history? Loads&Offers in one tab.\")")).click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();
        checkVideo(numberVideo);

        // Відкриває відео How to use the load's chat?
        numberVideo = 7;
        WebElement loadsChat = driver.findElement(AppiumBy.accessibilityId("How to use the load's chat?"));
        loadsChat.click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();
        checkVideo(numberVideo);

        // Відкриває відео How to upload BOL & POD? Part 1. Full flow
        numberVideo = 8;
        WebElement uploadBolPod = driver.findElement(AppiumBy.accessibilityId("How to upload BOL & POD? Part 1. Full flow"));
        uploadBolPod.click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();
        checkVideo(numberVideo);

        // Відкриває відео How to upload BOL & POD? Part 2. No BOL + Documents rejection
        numberVideo = 9;
        WebElement uploadBolPodDocument = driver.findElement(AppiumBy.accessibilityId("How to upload BOL & POD? Part 2. No BOL + Documents rejection"));
        uploadBolPodDocument.click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='More']")).click();
//        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Show less']")).click();
        checkVideo(numberVideo);
    }

    public void checkVideo(Integer numberVideo) {

        AndroidDriver driver = openApp.driverAndroid;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
        WebElement replayButton = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Replay Video"))
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Replay Video']"))
        );

        System.out.println(numberVideo + " номер. Відео успішно переглянуто");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
