package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.sleep;
import static java.time.Duration.ofMillis;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

public class MAD006_InteractiveTutorial {
    GlobalLoginMob openApp = new GlobalLoginMob();
    Integer numberVideo;

    @Test
    public void interactiveTutorial() throws Exception {

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

        clickAccessibilityId("Replay interactive tutorial");

        // Welcome
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Welcome\")"));
        driver.findElement(AppiumBy.accessibilityId("to the Driver's App Tutorial!"));
        clickAccessibilityId("Let's begin!");

        // My profile
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"MY PROFILE\")"));
        clickAccessibilityId("In the My Profile section, you will find the following information:");
        clickAccessibilityId("Let's go!");

        // Unit Number клік Next
        clickCoordinates(900, 2170);

        // Кількість виконаних грузів клік Next
        clickCoordinates(900, 2170);

        // Активний груз клік Next
        clickCoordinates(900, 2170);

        // Нові офери клік Next
        clickCoordinates(900, 2170);

        // Саппорт клік Next
        clickCoordinates(900, 2170);

        // Навбар клік Next
        clickCoordinates(900, 2170);

        // Оффери клік Next
        clickCoordinates(900, 2170);

        // Нові оффери клік Next
        clickCoordinates(900, 2170);

        // Прийняті заявки клік Next
        clickCoordinates(900, 2170);

        // Booked offers клік Next
        clickCoordinates(900, 2170);

        // Offers Archive клік Next
        clickCoordinates(900, 2170);

        // Перегляд відео How to accept an offers
        numberVideo = 1;
        checkVideo(numberVideo);
        driver.findElement(AppiumBy.accessibilityId("Next")).click();

        // Перегляд відео How to reject the offer?
        numberVideo = 2;
        checkVideo(numberVideo);
        driver.findElement(AppiumBy.accessibilityId("Go to Loads")).click();

        // My loads клік Next
        clickCoordinates(900, 2170);

        // Active loads клік Next
        clickCoordinates(900, 2170);

        // Archive loads клік Next
        sleep(1000);
        clickCoordinates(900, 2170);

        // Перегляд відео How to check load history?
        numberVideo = 3;
        checkVideo(numberVideo);
        driver.findElement(AppiumBy.accessibilityId("Finish")).click();

        // Перевіряє відображення Active Loads
        driver.findElement(AppiumBy.accessibilityId("Active Loads"));
    }

    public void clickAccessibilityId(String text) {
        AndroidDriver driver = openApp.driverAndroid;

        WebElement element = driver.findElement(AppiumBy.accessibilityId(text));
        element.click();
    }

    public void clickCoordinates(Integer X, Integer Y) {

        sleep(2000);
        AndroidDriver driver = openApp.driverAndroid;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), X, Y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(asList(tap));
    }

    public void checkVideo(Integer numberVideo) {

        AndroidDriver driver = openApp.driverAndroid;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
        WebElement replayButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.Button[@text='Replay Video']"))
//                ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Replay Video"))
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