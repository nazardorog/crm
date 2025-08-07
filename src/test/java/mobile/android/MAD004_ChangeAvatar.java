package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;

import static com.codeborne.selenide.Selenide.sleep;
import static java.time.Duration.ofMillis;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;

public class MAD004_ChangeAvatar {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void changeAvatar() throws Exception {

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // Завантажує аватар на девайс з проєкту
        uploadFileToEmulator();

        // Кейс 1. Завантажує фото на емулятор. Додає аватар з галереї. Видаляє аватар. Видаляє завантажене фото з емулятора
        // Клік по аватар
        clickCoordinates(150, 400);
//        WebElement avatar = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(9)"));
//        avatar.click();

        // Клік по камера
        WebElement camera = driver.findElement(By.xpath("//android.view.View/android.widget.ImageView[1]"));
        camera.click();

        // Клік по Галерея
        sleep(1000);
        WebElement gallery = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc='Gallery']"));
        gallery.click();

        // Клік по Фото
        WebElement photo = driver.findElement(By.xpath("//android.widget.TextView[@content-desc='Photos']"));
        photo.click();

        // Клік по Pictures
        sleep(1000);
        WebElement pictures = driver.findElement(By.xpath("//android.widget.RelativeLayout"));
        pictures.click();

        // Клік по завантаженому зображенню
        WebElement selectPhoto = driver.findElement(By.xpath("//android.view.ViewGroup[contains(@content-desc, 'Photo taken on')]"));
        selectPhoto.click();

        // Клік кнопка Looks good
        WebElement looksGood = driver.findElement(AppiumBy.accessibilityId("Looks good"));
        looksGood.click();

        // Перевіряє месседж "Profile photo successfully saved"
        By successMessage = By.xpath("//android.view.View[@content-desc='Profile photo successfully saved']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

        // Видаляє аватар
        deleteAvatarPhoto();

        // Кейс 2. Завантажує фото через фото камери. Додає фото з камери на аватар. Видаляє аватар
        // Клік по аватар для завантаження фото з камери девайса
        sleep(5000);
        WebElement avatarTelephoneCamera = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(10)"));
        avatarTelephoneCamera.click();

        // Клік по Галерея
        sleep(1000);
        WebElement galleryTelephoneCamera = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"));
        galleryTelephoneCamera.click();

        // Change profile photo. Клік по камера
        WebElement profileTelephoneCamera = driver.findElement(AppiumBy.accessibilityId("Camera"));
        profileTelephoneCamera.click();

        // Клік по фотоаппарат для здійснення фото з камери
        WebElement photoTelephoneCamera = driver.findElement(AppiumBy.accessibilityId("Shutter"));
        photoTelephoneCamera.click();

        // Клік кнопка підтвердження вибору фото
        WebElement checkTelephoneCamera = driver.findElement(AppiumBy.accessibilityId("Done"));
        checkTelephoneCamera.click();

        // Клік по Looks good
        sleep(5000);
        WebElement looksGoodTelephoneCamera = driver.findElement(AppiumBy.accessibilityId("Looks good"));
        looksGoodTelephoneCamera.click();

        // Перевіряє месседж "Profile photo successfully saved"
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));

        // Видаляє аватар
        deleteAvatarPhoto();
    }

    public void uploadFileToEmulator() throws Exception {

        // Отримує драйвер
        AndroidDriver driver = openApp.driverAndroid;

        // Шлях до локального файлу та в емуляторі
        String fileName = "avatar.jpg";
        String filePathLocal = "src/test/resources/fileMobile/";
        String filePathEmulator = "/sdcard/Pictures/";

        File fileLocal = new File(filePathLocal + fileName);
        byte[] fileContent = Files.readAllBytes(fileLocal.toPath());

        // Кодуємо в base64
        String base64Content = Base64.encodeBase64String(fileContent);

        //Завантажити у внутрішнє сховище Android
        driver.pushFile(filePathEmulator + fileName, base64Content.getBytes());
    }

    public void deleteFileFromEmulator() throws IOException {

        String filePathEmulator = "/sdcard/Pictures/avatar.jpg";
        String adbCommand = "adb shell rm " + filePathEmulator;
        Runtime.getRuntime().exec(adbCommand);
    }

    public void deleteAvatarPhoto() throws IOException {

        AndroidDriver driver = openApp.driverAndroid;

        // Клік по аватар для видалення
        sleep(5000);
        WebElement avatarEdit = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(10)"));
        avatarEdit.click();

        // Клік по корзина
        WebElement dell = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(0)"));
        dell.click();

        // Перевіряє шапку модального вікна "Remove photo"
        By successRemovePhoto = By.xpath("//android.view.View[@content-desc='Remove photo']");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(successRemovePhoto));

        // Підтверджує видалення фото
        WebElement remove = driver.findElement(AppiumBy.accessibilityId("Remove"));
        remove.click();

        // Видаляє завантажене фото з емулятора
        deleteFileFromEmulator();
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
