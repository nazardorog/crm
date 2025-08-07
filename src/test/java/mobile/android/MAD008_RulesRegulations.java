package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.testng.Assert;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import java.io.IOException;
import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;
import static java.time.Duration.ofMillis;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

public class MAD008_RulesRegulations {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void loginMobile() throws IOException {

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // Закриває додаток Docs перед тестом
        driver.terminateApp("com.google.android.apps.docs.editors.docs");

        // Очищає папку Download перед тестом
        String filePathEmulator = "/sdcard/Download/*";
        String adbCommand = "adb shell rm " + filePathEmulator;
        Runtime.getRuntime().exec(adbCommand);

        // Кейс1
        // More
        WebElement element = driver.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
        element.click();

        // Rules and regulations
        driver.findElement(AppiumBy.accessibilityId("Rules and regulations")).click();

        // Перевіряє відкриття документу
        WebElement moreElement = driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\"com.google.android.apps.docs.editors.docs:id/ink_container\"]\n"));
        Assert.assertTrue(moreElement.isDisplayed(), "Документ не відкрився");

        // Перевіряє наявність кнопки редагування, якщо не має клік для редагування
        List<WebElement> elements = driver.findElements(By.xpath("//com.google.android.material.floatingactionbutton.FloatingActionButton[@content-desc=\"Edit\"]"));
        if (elements.isEmpty()) {
            moreElement.click();
        }

        // Завантажує документ
        clickCoordinates(1000, 150);

        WebElement makeACopy = driver.findElement(By.xpath("//android.widget.ListView/android.widget.LinearLayout[10]"));
        makeACopy.click();

        // Вибирає Device
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Device\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Save\")")).click();
        sleep(1000);
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"SAVE\")")).click();

        // Перевіряє що файл завантажено успішно
        String filePath = "/sdcard/Download/Rules and regulations.docx";
        try {
            byte[] fileData = driver.pullFile(filePath);
            System.out.println("Файл знайдено. Розмір: " + fileData.length + " байт");
        } catch (Exception e) {
            System.out.println("Файл не знайдено.");
        }

        // Закриває додаток Docs перед тестом
        driver.terminateApp("com.google.android.apps.docs.editors.docs");
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

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
