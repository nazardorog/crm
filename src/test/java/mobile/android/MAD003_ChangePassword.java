package mobile.android;

import io.appium.java_client.android.AndroidDriver;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.Duration;

import static utilsMobile.configMobile.GlobalLoginMob.getPassword;

public class MAD003_ChangePassword {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void changePassword() throws IOException {

        // Open App
        String user = "driver_mob2";
        openApp.login(user);
        AndroidDriver driver = openApp.driverAndroid;

        // Old pass and generate New pass
        String oldPassword = getPassword(user);
        String newPassword = generatePassword(oldPassword);

        // More
        WebElement moreElement = driver.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
        Assert.assertTrue(moreElement.isDisplayed(), "Елемент 'More Tab 3 of 3' не відображається");
        moreElement.click();

        // More Change Password
        WebElement logoutButton = driver.findElement(By.xpath("//android.view.View[@content-desc='Password change']"));
        logoutButton.click();

        // Поля форми
        WebElement passwordOld = driver.findElement(By.xpath("//android.widget.EditText[@hint='Current password \nCurrent password']"));
        WebElement passwordNew = driver.findElement(By.xpath("//android.widget.EditText[@hint='New password']"));
        WebElement passwordConfirm = driver.findElement(By.xpath("//android.widget.EditText[@hint='Confirm new Password']"));

        // *1. Перевіряє валідацію різних нових паролів*
        // 1. Old password
        passwordOld.click();
        passwordOld.sendKeys(oldPassword);

        // 1. New password
        passwordNew.click();
        passwordNew.sendKeys(newPassword);

        // 1. Confirm password
        passwordConfirm.click();
        passwordConfirm.sendKeys("incorrectPassword2");

        // 1. Button Change Password
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Change Password']")).click();

        // 1. Перевіряє валідацію різних нових паролів, повідомлення поля Confirm new password
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.View[@content-desc='Required field']")
                )
        );

        // 1. Перевіряє валідацію різних нових паролів, повідомлення справа зверху кнопки Change password
        By partialText = By.xpath("//android.view.View[contains(@content-desc, 'Passwords don’t match')]");
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(partialText));

        // *2. Перевіряє валідацію не корректний поточний пароль*
        // 2. Вводить корректний Confirm new password
        passwordConfirm.click();
        passwordConfirm.sendKeys(newPassword);

        // 2. Вводить не корректний Current password
        passwordOld.click();
        passwordOld.clear();
        passwordOld.sendKeys("incorrect");

        // 2. Кнопка Change Password
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Change Password']")).click();

        // 2. Перевіряє валідацію не корректний поточний пароль
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//android.view.View[@content-desc='The old password is not valid']")
                )
        );

        // *Вводить корректні паролі*
        // 3. Коректний Current password
        passwordOld.click();
        passwordOld.clear();
        passwordOld.sendKeys(oldPassword);

        // 3. Кнопка Change Password
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Change Password']")).click();
        driver.findElement(By.xpath("//android.widget.Button[@content-desc='Ok']")).click();

        // Записує новий пароль в credentials.json
        setPassword(user, newPassword);

        // More
        moreElement.click();

        // Log Out
        WebElement LogOut = driver.findElement(By.xpath("//android.view.View[@content-desc='Log Out']"));
        LogOut.click();

        // Логіниться з новим зміненим паролем
        openApp.login(user);

        driver = openApp.driverAndroid;

        // Перевіряє що з"явилась іконка My profile
        WebElement myProfileElement = driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));
        Assert.assertTrue(myProfileElement.isDisplayed(), "Елемент 'More Tab 1 of 3' не відображається");

        // Виходить з авторизації
        WebElement moreElement2 = driver.findElement(By.xpath("//*[contains(@content-desc, 'More') and contains(@content-desc, 'Tab 3 of 3')]"));
        moreElement2.click();

        // Log Out
        WebElement LogOut2 = driver.findElement(By.xpath("//android.view.View[@content-desc='Log Out']"));
        LogOut2.click();
    }

    public static void setPassword(String user, String newPassword) {
        try {
            // Зберігає пароль в Target
            URL resource = GlobalLoginMob.class.getClassLoader().getResource("json/credentials.json");
            if (resource == null) {
                throw new FileNotFoundException("Файл credentials.json не знайдено в resources");
            }

            Path pathInTarget = Paths.get(resource.toURI());
            String content = new String(Files.readAllBytes(pathInTarget));
            JSONObject json = new JSONObject(content);

            // Оновлення паролю
            JSONObject userObj = json.has(user) ? json.getJSONObject(user) : new JSONObject();
            userObj.put("password", newPassword);
            json.put(user, userObj);

            // Шлях до src/test/resources
            Path pathInSrc = Paths.get("src/test/resources/json/credentials.json");

            // Запис у обидва файли
            Files.write(pathInTarget, json.toString(4).getBytes());
            Files.write(pathInSrc, json.toString(4).getBytes());

            System.out.println("Пароль успішно оновлено для користувача: " + user);

        } catch (Exception e) {
            throw new RuntimeException("Помилка запису пароля в credentials.json: " + e.getMessage(), e);
        }
    }

    public static String generatePassword(String password) {

        // розділяє старий пасс на літери та цифри
        String prefix = password.replaceAll("\\d", "");
        String numberPart = password.replaceAll("\\D", "");

        int number = Integer.parseInt(numberPart);
        number += 1;

        // Зберегти формат
        String newNumberPart = String.format("%0" + numberPart.length() + "d", number);

        return prefix + newNumberPart;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
