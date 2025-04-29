package web.bigTruck.brockers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BigTruckTestCase3Brockers {

    // Click Up:
    // CRM SEMI Truck
    // Brockers
    // 3. Change DNU category

    String brokerMcNumberBigTruck;
    String ffMcNumberBigTruck;
    String brokerNameBigTruck;
    String brokerDbaNameBigTruck;
    String brokerPhoneNumberBigTruck;
    String agentNameBigTruck;
    String agentMailBigTruck;
    String agentPhoneNumberBigTruck;

    @Test
    public void newBrokersBigTruck() throws InterruptedException {

        System.out.println("BigTruckTestCase3Brockers - Start");

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

        //фрейм Add Broker приховуємо warning блок
        SelenideElement warningBlock = $(".has-success .warning-block-wrapper");
        executeJavaScript("arguments[0].style.display='none';", warningBlock);

        //фрейм Add Broker кнопка Submit, закриття фрейму Add broker
        $("#add_broker_send").shouldBe(enabled).click();
        $("#add_broker").shouldNotBe(visible, Duration.ofSeconds(20));
        $("#add_load").shouldBe(visible, Duration.ofSeconds(10));

        //перевіряє створеного брокера в полі Broker
        $("#select2-broker_search-container").shouldBe(text(brokerNameBigTruck + " | " + brokerDbaNameBigTruck));

        //фрейм New load вибирає Агента в полі Select Agent
        $("#select2-broker-agent-load-select-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__options")
                .findBy(text(agentNameBigTruck))
                .click();

        //закриває фрейм New load
        $(".modal-new-load-bigtrucks .close").click();
        $(".modal-new-load-bigtrucks").shouldNotBe(visible, Duration.ofSeconds(10));

        //переходить на список брокерів
        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();

        //шукає брокера по MC
        $("#brokerssearch-mc_number").setValue(brokerMcNumberBigTruck).pressEnter();

        //перевіряє що брокера знайдено
        SelenideElement rowBroker = $$("table.table-brokers-style-profile tbody tr")
                .findBy(text(brokerMcNumberBigTruck))
                .shouldBe(visible);

        //перевіряє що DNU не встановлено для брокера
        rowBroker.$("td", 8).shouldHave(text("Active"));

        //праве меню вибирає Add to DNU
        rowBroker.$(".btn-action-more").click();
        $$(".dropdown-menu-right a").findBy(text("Add to DNU")).shouldBe(visible, enabled).click();

        //фрейм DNU встановлює DNU для брокера
        $("#broker_dnu_modal .bootstrap-dialog-title").shouldBe(visible);
        $("#category-dropdown").selectOption("4 - Dry Van Only");
        $("#category-dropdown").getSelectedOption().shouldHave(Condition.text("4 - Dry Van Only"), Duration.ofSeconds(10));
        $("#brokers-comment").shouldBe(visible, Duration.ofSeconds(20));
        $("#brokers-comment").shouldBe(enabled, Duration.ofSeconds(20));
        sleep(10000);
        $("#brokers-comment").setValue("DNU reason massage").shouldHave(enabled, Duration.ofSeconds(10)).pressEnter();
        $("#brokers-comment").shouldHave(value("DNU reason massage"), Duration.ofSeconds(10));

        //закриває фрейм DNU
        $("#broker_from_dnu_send").click();
        $("#brokers-blacklist-form").shouldNotBe(visible, Duration.ofSeconds(10));

        //перевіряє що DNU встановлено для брокера
        rowBroker.$("td", 8).shouldHave(text("DNU"));


        System.out.println("BigTruckTestCase3Brockers - Test Pass");
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
