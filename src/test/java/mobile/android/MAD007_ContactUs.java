package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsMobile.configMobile.GlobalLoginMob;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static java.time.Duration.ofMillis;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

public class MAD007_ContactUs {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void interactiveTutorial() throws Exception {

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // More
        WebElement moreElement = driver.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
        Assert.assertTrue(moreElement.isDisplayed(), "Елемент 'More Tab 3 of 3' не відображається");
        moreElement.click();

        // Вибирає Contact Us
        WebElement logoutButton = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Contact Us']"));
        logoutButton.click();

        // Кейс 1. Відправлення повідомлень 4 особам
        sendMessage("Contact accounting");
        sendMessage("Contact HR Agent");
        sendMessage("Update paperwork");
        sendMessage("Report app issue");

        // Кейс 2. Перевірка контактних номерів
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"(800) 985-0888\")"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Ext.4\")"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Ext.5\")"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Ext.2\")"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Ext.7\")"));

        // Кейс 3. Перехід на сайт
        swipeUp();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().description(\"Visit our site\")")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> ((AndroidDriver) d).getCurrentPackage().equals("com.android.chrome"));

        WebElement sait = driver.findElement(AppiumBy.id("com.android.chrome:id/url_bar"));
        String text = sait.getText();
        System.out.println("URL в Chrome: " + text);
        Assert.assertTrue(text.contains("empirenational.com"), "Сайт не відкрився!");
    }

    public void swipeUp() {
        AndroidDriver driver = openApp.driverAndroid;

        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();

        int startX = screenWidth / 2;
        int startY = (int) (screenHeight * 0.8);
        int endY = (int) (screenHeight * 0.2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }

    private void sendMessage(String subject) {
        AndroidDriver driver = openApp.driverAndroid;

        // Кнопка Send message
        WebElement sendMessage = driver.findElement(By.xpath("//android.widget.Button[@content-desc='Send message']"));
        sendMessage.click();

        // Поле вибору Message subject
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), 970, 550));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(asList(tap));

        WebElement contactAccounting = driver.findElement(AppiumBy.accessibilityId(subject));
        contactAccounting.click();

        List<WebElement> inputFields = driver.findElements(By.className("android.widget.EditText"));
        inputFields.get(0).click();
        inputFields.get(0).sendKeys("massage:" + subject);

        // Кнопка Send message
        WebElement sendAccounting = driver.findElement(AppiumBy.accessibilityId("Send message"));
        sendAccounting.click();

        // Кнопка Ok Sent successfully
        WebElement sentSuccessfully = driver.findElement(AppiumBy.accessibilityId("Ok"));
        sentSuccessfully.click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
