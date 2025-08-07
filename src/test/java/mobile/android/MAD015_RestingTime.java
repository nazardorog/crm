package mobile.android;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.configWeb.GlobalLogin;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class MAD015_RestingTime {
    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void restingTime() throws IOException {

        final String crmDriver = "[sys]027_driver Driver";

        // Mob. Open App
        openApp.login("driver_mob8");
        AndroidDriver driver = openApp.driverAndroid;

        // Mob. My Profile
        sleep(2000);
        WebElement myProfile = driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));
        Assert.assertTrue(myProfile.isDisplayed(), "Елемент My profile не відображається");
        myProfile.click();

        // Mob. Перевіряє чи водій не на Rest, якщо так - то знімає з Rest
        List<WebElement> restStatus = driver.findElements(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Resting till')]"));
        if (!restStatus.isEmpty()) {
            restStatus.get(0).click();
        }

        // Кейс 1. Відправляє з CRM пуш до водія й перевіряє що пуш відображається в додатку та в шторці
        // CRM. Перевіряє що надходять пуши до водія
        GlobalLogin.login("exp_disp1");
        $(".logo-mini-icon").click();

        // CRM. Load board
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("#main-loads-grid-filters").shouldBe(visible, EXPECT_GLOBAL);
        $("input[name='LoadsSearch[driver_carrier]']").shouldBe(visible).setValue(crmDriver).pressEnter();

        // CRM. Відправляє пуш до водія
        SelenideElement rowLoadMessage = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver));
        rowLoadMessage.$(".chat-btn-loads").click();
        $("textarea.chat-send-message__message").setValue("Пуш доходить");
        $("[title='Start recording']").click();

        // Mob. Перевіряє що пуш доходить в додатку поки водій не на rest
        driver.findElement(By.xpath("//*[contains(@content-desc, 'Пуш доходить')]")).click();
        sleep(2000);
        driver.navigate().back();

        // Mob. Перевіряє що пуш в верхній шторці доходить, поки водій не на rest
        driver.openNotifications();
        sleep(2000);
        driver.findElement(By.xpath("//android.widget.TextView[@resource-id=\"android:id/text\" and @text=\"Пуш доходить\"]"));
        driver.navigate().back();

        // Кейс 2. Ставить водія на відпочинок. Перевіряє що з CRM пуш до водія в додаток доходить і в шторці не відображається. Перевіряє на CRM відображення червоним кольором тексту "Resting until" в лоад борд
        // Mob. Перевіряємо чи вже доступний для зміни Resting time
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));

        int numberCircles = 15;
        int attempt = 0;
        while (attempt < numberCircles) {
            driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Set resting time\"]")).click();
            List<WebElement> element = driver.findElements(By.xpath("//android.widget.ImageView[@content-desc=\"For accurate tracking, resting mode updates are limited to once per minute\"]"));

            if (element.isEmpty()) {
                break;
            }
            else {
                sleep(4000);
                attempt++;
            }
        }

        // Mob. Встановлює Set resting time
        driver.findElement(AppiumBy.accessibilityId("Today")).click();
        driver.findElement(AppiumBy.accessibilityId("Set")).click();
        driver.findElement(AppiumBy.accessibilityId("Rest"));

        // Mob. Перевіряє що водія поставлено на відпочинок
        driver.findElement(By.xpath("//*[contains(@content-desc, 'Resting till') and contains(@content-desc, '6:00 AM')]"));

        // CRM. Перевіряє що водія поставлено на відпочинок
        // CRM. Load board
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("#main-loads-grid-filters").shouldBe(visible, EXPECT_GLOBAL);
        $("input[name='LoadsSearch[driver_carrier]']").shouldBe(visible).setValue(crmDriver).pressEnter();

        // CRM. Перевіряє що водій на відпочинку й текст червоного кольору
        SelenideElement rowLoad = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver));
        rowLoad.$(".view_driver").shouldHave(text(crmDriver));
        rowLoad.$(".broker_note").shouldHave(text("Resting until"));
        String color = rowLoad.$(".broker_note").getCssValue("color");
        rowLoad.$(".broker_note").shouldHave(Condition.cssValue("color", "rgba(255, 0, 0, 1)"));

        // CRM. Відправляє повідомлення. Не повинно дійти в шторку коли водій на відпочинку
        $("textarea.chat-send-message__message").setValue("в шторку не дійде Пуш");
        $("[title='Start recording']").click();

        // Mob. Перевіряє що пуш є в додатку та не має в шторці
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'в шторку не дійде Пуш')]"));

        // Mob. Перевіряє що пуш не доходить в шторку коли водій на rest
        driver.openNotifications();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        boolean isNotVisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//android.widget.TextView[contains(@content-desc, 'в шторку не дійде Пуш')]")
        ));
        Assert.assertTrue(isNotVisible, "Елемент з текстом 'в шторку не дійде Пуш' відображається а не має відображатися.");
        driver.navigate().back();

        // Mob. Повертає водія з відпочинку для наступного запуску тесту
        sleep(2000);
        List<WebElement> noRestStatus = driver.findElements(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Resting till')]"));
        if (!noRestStatus.isEmpty()) {
            noRestStatus.get(0).click();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        CloseWebDriver.tearDown();

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
