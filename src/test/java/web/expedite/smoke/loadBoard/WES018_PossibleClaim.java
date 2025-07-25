package web.expedite.smoke.loadBoard;

import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.SelenideElement;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

@Listeners(utilsWeb.commonWeb.Listener.class)
@Epic("Expedite")
@Feature("Smoke")
public class WES018_PossibleClaim {
    // https://app.clickup.com/t/86990x7m0
    // Possible Claim / перевод трака в load issue

    @Test(description = "тест в description")
    @Story("Load board")
    @Description("дескріпш")
    @Severity(SeverityLevel.CRITICAL)
    public void possibleClaim() {

        // Встановлюємо кастомну назву для тесту
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Possible Claim / перевод трака в load issue");
        });

        // Login
        GlobalLogin.login("exp_disp1");

        String proNumber = NewLoad.expedite();
        
        // Поиск груза
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();

        String[] optionValues = {"1", "2", "3", "4"};
        String[] expectedTexts = {"Load damaged", "Stolen truck", "Shortage", "Accidents"};

        for (int i = 0; i < optionValues.length; i++) {
            String value = optionValues[i];
            String expectedText = expectedTexts[i];

            // Выбор опции
            $(".btn-group").shouldBe(visible).click();
            $(".issue_variants").shouldBe(visible).click();
            SelenideElement dropdown = $(By.cssSelector("#loads-issue_type"));
            dropdown.shouldBe(visible);
            dropdown.selectOptionByValue(value);
            $("#issue_variants_send").click();

            // Переход на вкладку Loads Issue
            $(".li-tabs-home.li-tabs-issue").shouldBe(visible, EXPECT_5).click();
            $("td.our_pro_number").shouldHave(text(proNumber));

            // Проверка наличия выбранной опции
            $$("tbody tr").findBy(text(expectedText)).shouldBe(visible);

            // Перевод трака в Loads en Route
            $(".btn-group").shouldBe(visible).click();
            $(".return_status_en_route").shouldBe(visible).click();
            $("#loadnotes-note").shouldBe(visible, EXPECT_5).setValue("Possible claim bounce to en route");
            $("#return_en_route_send").click();

            // Переход на вкладку Loads en Route и проверка наличия груза
            $(".li-tabs-home.li-tabs-route").shouldBe(visible).click();
            $("td.our_pro_number").shouldBe(visible, EXPECT_5).shouldHave(text(proNumber));
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
