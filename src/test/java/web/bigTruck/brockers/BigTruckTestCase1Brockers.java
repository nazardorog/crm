package web.bigTruck.brockers;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase1Brockers {

    // Click Up:
    // CRM SEMI Truck
    // Brockers
    // 1. Создание Брокера

    String brokerMcNumberBigTruck;
    String ffMcNumberBigTruck;
    String brokerNameBigTruck;
    String brokerDbaNameBigTruck;
    String brokerPhoneNumberBigTruck;
    String agentNameBigTruck;
    String agentMailBigTruck;
    String agentPhoneNumberBigTruck;

    @Test
    public void newBrokersBigTruck() throws InterruptedException{

        System.out.println("BigTruckTestCase1Brockers - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        Random random = new Random();
        int randomNumber = random.nextInt(1000);
        int randomNumberMc = random.nextInt(10000);
        generateDataNewBroker(randomNumber, randomNumberMc);

        //створює новий вантаж
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        //відкриває створення брокера, клац по "+" біля поля Broker
        $("#new_broker").shouldBe(visible, Duration.ofSeconds(30)).click();

        //фрейм Add Broker вкладка General
        $("#brokers-mc_number")
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldBe(enabled)
                .setValue(brokerMcNumberBigTruck);
        $("#brokers-name").setValue(brokerNameBigTruck);
        $("#brokers-dba_name").setValue(brokerDbaNameBigTruck);
        $("#brokers-entity_type").shouldBe(visible).selectOption("BROKER");
        $("#brokers-address").setValue("Mountain");
        $("#brokers-second_mc_number").setValue(ffMcNumberBigTruck);
        $("#brokers-city").setValue("Colorado");
        $("#brokers-custom_limit").setValue("" + randomNumber);
        $("#brokers-note").setValue("AutoTestBrockersNote");

        //фрейм Add Broker вкладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldBe(visible).setValue("Broker contact main Name");
        $("#brokers-main_phone_number").setValue(brokerPhoneNumberBigTruck);
        $("#brokers-main_email").setValue("BrokerContact" + randomNumber + "@mail.com");

        //фрейм Add Broker вкладка Agents
        $(byText("Agents")).click();
        $("#agents-0-name").setValue(agentNameBigTruck);
        $("#agents-0-last_name").setValue("Agent Last Name" + randomNumber);
        $("#agents-0-email").setValue(agentMailBigTruck);
        $("#agents-0-phone_number").setValue(agentPhoneNumberBigTruck);
        $("#agent-cell_phone-update0").setValue(brokerPhoneNumberBigTruck + "01");

        //фрейм Add Broker кнопка Submit
        $("#add_broker_send").shouldBe(enabled).click();

        //перевіряє створеного брокера в полі Broker
        $("#select2-broker_search-container").shouldBe(text(brokerNameBigTruck + " | " + brokerDbaNameBigTruck));

        //фрейм New load вибирає Агента в полі Select Agent
        $("#select2-broker-agent-load-select-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__options")
                .findBy(text(agentNameBigTruck))
                .click();

        //перевіряє створеного агента
        $("#select2-broker-agent-load-select-container").shouldHave(text(agentNameBigTruck));
        $("#select2-broker-agent-load-select-container").shouldHave(text("Agent Last Name" + randomNumber));
        $(".bt-load-broker-main-flex").shouldHave(text("Mountain"));
        $(".bt-load-broker-main-flex").shouldHave(text("Colorado"));
        $(".bt-load-broker-main-flex").shouldHave(text(brokerPhoneNumberBigTruck));


        System.out.println("BigTruckTestCase1Brockers - Test Pass");
    }

    void generateDataNewBroker(int randomNumber, int randomNumberMc){

        //генеруємо дані для створення брокера
        brokerMcNumberBigTruck = "" + randomNumberMc + randomNumber;
        ffMcNumberBigTruck = "FF" + brokerMcNumberBigTruck;
        brokerNameBigTruck = "AutoTestBrokerBigTruck" + randomNumber;
        brokerDbaNameBigTruck = "AutoTestBrokerBigTruckDba" + randomNumber;
        brokerPhoneNumberBigTruck = "(056) 333-3" + randomNumber;
        agentNameBigTruck = "Agent_" + brokerNameBigTruck;
        agentMailBigTruck = agentNameBigTruck + "@mail.com";
        agentPhoneNumberBigTruck = "(056) 335" + randomNumber + "01";
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        System.out.println("Tear down - close WebDriver");
        web.config.CloseWebDriver.tearDown();
    }
}
