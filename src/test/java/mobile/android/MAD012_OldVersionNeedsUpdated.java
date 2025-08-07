package mobile.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.sleep;

public class MAD012_OldVersionNeedsUpdated {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void versionNeedsUpdated() throws IOException {

        // Встановлює стару версію застосунку
        installOldVersion();

        // Open App
        openApp.login("driver_mob1");
        AndroidDriver driver = openApp.driverAndroid;

        // Перевіряє повідомлення про необхідність оновлення
        driver.findElement(AppiumBy.accessibilityId("New version"));
        driver.findElement(AppiumBy.accessibilityId("(current version 1.0.73(610))"));
        driver.findElement(AppiumBy.accessibilityId("Close"));
        driver.findElement(AppiumBy.accessibilityId("Update")).click();

        // Перевіряє перехід в PlayMarket
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.View\").instance(30)"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"EN Expedite\")"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Empire National Inc\")"));
    }

    public void installOldVersion() throws IOException {

        openApp.driverCreate();

        // Видаляє нову версію застосунку
        String adbCommandDell = "adb uninstall com.empirenational.gl.driver";
        Runtime.getRuntime().exec(adbCommandDell);
        sleep(1000);

        // Встановлює стару версію застосунку
        String adbCommandAdd = "adb install C:/autotest_v1/empire/apk/driver/app-release-610.apk";
        Runtime.getRuntime().exec(adbCommandAdd);
        sleep(3000);
    }

    public void installNewVersion() throws IOException {

        openApp.driverCreate();

        // Видаляє нову версію застосунку
        String adbCommandDell = "adb uninstall com.empirenational.gl.driver";
        Runtime.getRuntime().exec(adbCommandDell);
        sleep(1000);

        // Встановлює стару версію застосунку
        String adbCommandAdd = "adb install C:/autotest_v1/empire/apk/driver/app-release.apk";
        Runtime.getRuntime().exec(adbCommandAdd);

        sleep(4000);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws IOException {
        installNewVersion();

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
