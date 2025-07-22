package web.bigTruck.smoke.broker;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS018_BrokerCreate {

    // Click Up:
    // CRM SEMI Truck
    // Brockers
    // 1. Создание Брокера

    // Global data
    String globalNumberSeven = GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test(description = "тест в description")
    @Story("Broker")
    @Description("дескріпш")
    @Severity(SeverityLevel.CRITICAL)
    public void create() throws InterruptedException{

        // Встановлюємо кастомну назву для тесту
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("1. Создание Брокера");
        });

        // Встановлюємо кастомну назву для тесту
        Allure.getLifecycle().updateTestCase(testResult -> {
            testResult.setName("Создание New Load с типом файла RateConfirmation");
        });

        // Login
        GlobalLogin.login("bt_disp1");

        // Data for creating a Broker
        final String mcNumber = globalNumberSeven;
        final String dbaName = globalName + "DBA Name";
        final String legalName = globalName + "Legal Name";
        final String entityType = "BROKER";
        final String address = "Mountain";
        final String city = "Colorado";
        final String setCreditLimit = globalNumberSeven;
        final String note = globalName + "Note";
        final String mainName = globalName + "Main Name";
        final String mail = globalMail;
        final String mainPhoneNumber = globalPhoneNumber;
        final String ffName = globalName + "2nd MC/FF Name";
        final String agentName = globalName + "Agent Name";
        final String agentLastName = globalName + "Agent Last Name";
        final String agentMail = "AgentMail_" + globalMail;
        final String agentPhoneNumber = globalPhoneNumber + "01";
        final String agentCellPhone = globalPhoneNumber + "02";

        // Create new load
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();

        // Відкриває створення брокера, клац по "+" біля поля Broker
        $("#new_broker").shouldBe(visible, Duration.ofSeconds(30)).click();

        // Фрейм Add Broker вкладка General
        $("#brokers-mc_number")
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldBe(enabled)
                .setValue(mcNumber);
        $("#brokers-dba_name").setValue(dbaName);
        $("#brokers-name").setValue(legalName);
        $("#brokers-entity_type").shouldBe(visible).selectOption(entityType);
        $("#brokers-second_mc_number").setValue(ffName);
        $("#brokers-address").setValue(address);
        $("#brokers-city").setValue(city);
        $("#brokers-custom_limit").setValue(setCreditLimit);
        $("#brokers-note").setValue(note);

        // Фрейм Add Broker вкладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldBe(visible).setValue(mainName);
        $("#brokers-main_phone_number").setValue(mainPhoneNumber);
        $("#brokers-main_email").setValue(mail);

        // Фрейм Add Broker вкладка Agents
        $(byText("Agents")).click();
        $("#agents-0-name").setValue(agentName);
        $("#agents-0-last_name").setValue(agentLastName);
        $("#agents-0-email").setValue(agentMail);
        $("#agents-0-phone_number").setValue(agentPhoneNumber);
        $("#agent-cell_phone-update0").setValue(agentCellPhone);

        // Фрейм Add Broker приховуємо warning блок
        SelenideElement warningBlock = $(".has-success .warning-block-wrapper");
        executeJavaScript("arguments[0].style.display='none';", warningBlock);

        // Фрейм Add Broker кнопка Submit, закриття фрейму Add broker
        $("#add_broker_send").shouldBe(clickable).click();
        $("#add_broker").shouldNotBe(visible, EXPECT_GLOBAL);

        // Перевіряє створеного брокера в полі Broker
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(text(legalName + " | " + dbaName));

        // Фрейм New load вибирає Агента в полі Select Agent
        $("#select2-broker-agent-load-select-container").shouldBe(visible).click();
        $(".select2-search__field").setValue(agentName);
        $$(".select2-results__options")
                .findBy(text(agentName))
                .click();

        // Перевіряє створеного Broker
        $("#select2-broker_search-container").shouldHave(text(legalName));
        $("#select2-broker_search-container").shouldHave(text(dbaName));

        // Перевіряє створеного Agent
        $("#select2-broker-agent-load-select-container").shouldHave(text(agentName));
        $("#select2-broker-agent-load-select-container").shouldHave(text(agentLastName));
        $(".bt-load-broker-main-flex").shouldHave(text(address));
        $(".bt-load-broker-main-flex").shouldHave(text(city));
        $(".bt-load-broker-main-flex").shouldHave(text(mainPhoneNumber));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
