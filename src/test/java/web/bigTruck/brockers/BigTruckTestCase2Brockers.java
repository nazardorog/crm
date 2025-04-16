package web.bigTruck.brockers;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class BigTruckTestCase2Brockers {

    // Click Up:
    // CRM SEMI Truck
    // Brockers
    // 1. Создание Брокера

    Random random = new Random();
    int randomNumber;
    int randomNumberMc;

    String brokerMcNumberBigTruck;
    String ffMcNumberBigTruck;
    String brokerNameBigTruck;
    String brokerDbaNameBigTruck;
    String brokerPhoneNumberBigTruck;
    String agentNameBigTruck;
    String agentMailBigTruck;
    String agentPhoneNumberBigTruck;
    boolean dataOptionEdit = false;

    @Test
    public void newBrokersBigTruck() throws InterruptedException{

        System.out.println("BigTruckTestCase2Brockers - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //відкриває New load
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        //відкриває створення брокера, клік по "+" біля поля Broker
        $("#new_broker").shouldBe(visible, Duration.ofSeconds(30)).click();

        //генерує дані та вводить. Створює Брокера
        randomNumber = random.nextInt(1000);
        randomNumberMc = random.nextInt(10000);
        generateDataNewBroker(randomNumber, randomNumberMc);
        inputDateBroker();

        //фрейм Add Broker приховуємо warning блок
        SelenideElement warningBlock = $(".has-success .warning-block-wrapper");
        executeJavaScript("arguments[0].style.display='none';", warningBlock);

        //закриває фрейм Add Broker
        SelenideElement modal = $("#add_broker");
        $("#add_broker_send").shouldBe(visible, enabled).click();
        modal.shouldNotBe(visible, Duration.ofSeconds(20));

        //фрейм New load перевіряє створеного брокера в полі Broker
        $("#select2-broker_search-container").shouldBe(text(brokerNameBigTruck + " | " + brokerDbaNameBigTruck), Duration.ofSeconds(10));

        //фрейм New load вибирає Агента в полі Select Agent
        $("#select2-broker-agent-load-select-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("BrokerBigTruck");
        $$(".select2-results__options")
                .findBy(text(agentNameBigTruck))
                .click();

        System.out.println("BigTruckTestCase2Brockers MCBroker:" + brokerMcNumberBigTruck + ". Broker name:" + brokerNameBigTruck);

        //перевіряє створеного агента
        $("#select2-broker-agent-load-select-container").shouldHave(text(agentNameBigTruck));
        $("#select2-broker-agent-load-select-container").shouldHave(text("Agent Last Name" + randomNumber));
        $(".bt-load-broker-main-flex").shouldHave(text("Mountain"));
        $(".bt-load-broker-main-flex").shouldHave(text("Colorado"));
        $(".bt-load-broker-main-flex").shouldHave(text(brokerPhoneNumberBigTruck));

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

        rowBroker.shouldHave(text(brokerNameBigTruck));

        //вибирає редагування брокера
        rowBroker.$(".btn-action-more").click();
        $(".dropdown-profile-style .update_broker_main").click();

        //перевіряє дані створеного брокера в списку брокерів
        validationDataBroker();

        //генерує дані для редагування та вводить
        dataOptionEdit = true;
        randomNumber = random.nextInt(1000);
        randomNumberMc = random.nextInt(10000);
        generateDataNewBroker(randomNumber, randomNumberMc);
        $(byText("General")).click();
        inputDateBroker();

        //закриває фрейм Update Broker
        SelenideElement updateModal = $("#update_broker");
        $("#update_broker_send").shouldBe(visible, enabled).click();
        updateModal.shouldNotBe(visible, Duration.ofSeconds(20));

        //шукає брокера по MC
        $("#brokerssearch-mc_number").setValue(brokerMcNumberBigTruck).pressEnter();

        //перевіряє дані брокера після редагування в списку брокерів
        rowBroker = $$("table.table-brokers-style-profile tbody tr")
                .findBy(text(brokerMcNumberBigTruck))
                .shouldBe(visible);

        rowBroker.shouldHave(text(brokerNameBigTruck), Duration.ofSeconds(20));

        System.out.println("BigTruckTestCase2Brockers MCBroker:" + brokerMcNumberBigTruck + ". Broker name:" + brokerNameBigTruck);

        //вибирає редагування брокера
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        rowBroker.$(".btn-action-more").click();
        $(".dropdown-profile-style .update_broker_main").click();

        //перевіряє дані створеного брокера
        validationDataBroker();

        web.config.CloseWebDriver.tearDown();
        System.out.println("BigTruckTestCase2Brockers - Test Pass");
    }

    void generateDataNewBroker(int randomNumber, int randomNumberMc){

        //генеруємо дані для створення брокера
        if (!dataOptionEdit){
            brokerMcNumberBigTruck = "" + randomNumberMc + randomNumber;
        }
        ffMcNumberBigTruck = "FF" + brokerMcNumberBigTruck;
        brokerNameBigTruck = "BrokerBigTruck" + randomNumber;
        brokerDbaNameBigTruck = "BrokerBigTruckDba" + randomNumber;
        brokerPhoneNumberBigTruck = "(057) 333-3" + randomNumber;
        agentNameBigTruck = "Agent_" + brokerNameBigTruck;
        agentMailBigTruck = agentNameBigTruck + "@" + randomNumber + "mail.com";
        agentPhoneNumberBigTruck = "(057) 335-5" + randomNumber;
    }

//    void inputDateBroker() {
//        inputDateBroker(false); // викликає метод з параметром
//    }

    void inputDateBroker(){

        //фрейм Add Broker вкладка General
        $("#brokers-mc_number")
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldBe(enabled);

        if (!dataOptionEdit){
            $("#brokers-mc_number")
                    .setValue(brokerMcNumberBigTruck);
        }

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

        if (dataOptionEdit){
            $(".edit_agent_btn").click();
        }

        $("#agents-0-name").setValue(agentNameBigTruck);
        $("#agents-0-last_name").setValue("Agent Last Name" + randomNumber);
        $("#agents-0-email").setValue(agentMailBigTruck);
        $("#agents-0-phone_number").setValue(agentPhoneNumberBigTruck);
        $("#agent-cell_phone-update0").setValue(brokerPhoneNumberBigTruck);
    }

    public void validationDataBroker (){

        //перевіряє дані брокера перед редагуванням фрейм Add Broker вкладка General
        $("#update_broker").shouldBe(visible, Duration.ofSeconds(20));
        $("#brokers-mc_number").shouldHave(value(brokerMcNumberBigTruck));
        $("#brokers-name").shouldHave(value(brokerNameBigTruck));
        $("#brokers-dba_name").shouldHave(value(brokerDbaNameBigTruck));
        $("#brokers-entity_type").getSelectedOption().shouldHave(Condition.text("BROKER"));
        $("#brokers-address").shouldHave(value("Mountain"));
        $("#brokers-second_mc_number").shouldHave(value(ffMcNumberBigTruck));
        $("#brokers-city").shouldHave(value("Colorado"));
        $("#brokers-custom_limit").shouldHave(value("" + randomNumber));
        $("#brokers-note").shouldHave(text("AutoTestBrockersNote"));

        //перевіряє дані брокера перед редагуванням фрейм Add Broker вкладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldHave(value ("Broker contact main Name"));
        $("#brokers-main_phone_number").shouldHave(value(brokerPhoneNumberBigTruck));
        $("#brokers-main_email").shouldHave(value("BrokerContact" + randomNumber + "@mail.com"));

        //фрейм Add Broker вкладка Agents
        $(byText("Agents")).click();
        $("#agents-0-name").shouldHave(value(agentNameBigTruck));
        $("#agents-0-last_name").shouldHave(value("Agent Last Name" + randomNumber));
        $("#agents-0-phone_number").shouldHave(value(agentPhoneNumberBigTruck + " ex _"));
        $("#agent-cell_phone-update0").shouldHave(value(brokerPhoneNumberBigTruck + " ex _"));
    }
}
