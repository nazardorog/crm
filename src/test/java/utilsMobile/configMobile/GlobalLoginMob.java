package utilsMobile.configMobile;

import com.codeborne.selenide.Selenide;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration; // Додайте цей імпорт
import java.util.List;

import static com.codeborne.selenide.Selenide.sleep;

public class GlobalLoginMob {

    public AndroidDriver driverAndroid;
    public static JSONObject credentials;

    public void driverCreate() throws MalformedURLException {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("appium:automationName", "UiAutomator2");
        capabilities.setCapability("appium:deviceName", "emulator-5554");
        capabilities.setCapability("appium:appPackage", "com.empirenational.gl.driver");
        capabilities.setCapability("appium:appActivity", "com.empirenational.gl.driver.MainActivity");
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:forceAppLaunch", true);
        capabilities.setCapability("appium:newCommandTimeout", 300);
        capabilities.setCapability("appium:chromedriverExecutable", "C:\\autotest_v1\\src\\test\\resources\\chromeDriver\\113\\chromedriver.exe");

        URL url = new URL("http://127.0.0.1:4723/");
        driverAndroid = new AndroidDriver(url, capabilities);

        // неявні очікування
        driverAndroid.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void login(String user) throws IOException {

        credentials();

        // Дані авторизації
        String login = getUsername(user);
        String password = getPassword(user);
        driverCreate();

        // Перевіряє чи потрібний доступ до локації
        checkOnLocation();

        List<WebElement> elUpdate = driverAndroid.findElements(By.xpath("//android.widget.Button[@content-desc=\"Update\"]"));

        // Якщо не з"явилась кнопка Update продовжуємо тест
        if (!elUpdate.isEmpty()) {
            return;
        }
        else {

            // Log out
            List<WebElement> elements = driverAndroid.findElements(By.xpath("//android.view.View[@content-desc='version 1.0.75(615)']/android.widget.ImageView[2]"));
            if (elements.isEmpty()) {
                // More
                WebElement element = driverAndroid.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
                element.click();

                // Log Out
                WebElement logoutButton = driverAndroid.findElement(By.xpath("//android.view.View[@content-desc='Log Out']"));
                logoutButton.click();
            }

            driverAndroid.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.ImageView\").instance(1)"));

            // Logo click 5 разів
            WebDriverWait wait = new WebDriverWait(driverAndroid, Duration.ofSeconds(10));
            for (int i = 0; i < 5; i++) {

                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//android.view.View[@content-desc='version 1.0.75(615)']/android.widget.ImageView[2]")
                ));

                element.click();
                Selenide.sleep(1000);
            }

            // Email
            List<WebElement> inputFields = driverAndroid.findElements(By.className("android.widget.EditText"));
            inputFields.get(0).click();
            inputFields.get(0).sendKeys(login);

            // Password
            inputFields.get(1).click();
            inputFields.get(1).sendKeys(password);

            // Server
            inputFields.get(2).click();
            driverAndroid.hideKeyboard();
            inputFields.get(2).clear();
            inputFields.get(2).sendKeys("https://preprod.empirenational.com/");

            driverAndroid.findElement(By.xpath("//android.widget.Button[@content-desc='Log in']")).click();

            Selenide.sleep(4000);
            checkOnLocation();
        }
    }

    public static JSONObject credentials() {

        // Read JSON
        try {
            String content = new String(GlobalLoginMob.class.getClassLoader().getResourceAsStream("json/credentials.json").readAllBytes());
            credentials = new JSONObject(content);
            return credentials;
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Ошибка загрузки credentials.json: " + e.getMessage());
        }
    }

    public static String getUsername (String user){
        return credentials.getJSONObject(user).getString("username");
    }

    public static String getPassword (String user){
        return credentials.getJSONObject(user).getString("password");
    }


    @AfterClass // Цей метод виконується один раз після всіх тестів в класі
    public void tearDown() {
        if (driverAndroid != null) {
            driverAndroid.quit();
        }
    }

    public void checkOnLocation() {

//        sleep(4000);
//        List<WebElement> elLocation = driverAndroid.findElements(AppiumBy.accessibilityId("Location Sharing"));
//
//        if (!elLocation.isEmpty()) {
//            System.out.println("good");
//            driverAndroid.findElement(By.xpath("//android.widget.Switch")).click();
//            driverAndroid.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"While using the app\")")).click();
//            driverAndroid.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"Allow all the time\")")).click();
//            driverAndroid.navigate().back();
//        }
    }
}
