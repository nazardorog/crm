package web.expedite.full;

import org.testng.annotations.AfterMethod;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class WEF015_BrokerCreateEdit {

    @Test
    public void create() throws InterruptedException{

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        //відкриваємо New Load
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();

        //генеруємо дані для створення брокера
        Random random = new Random();
        String brokerMcNumber = "" + random.nextInt(10000000);
        String brokerName = "AutoTestBroker" + String.format("%03d",random.nextInt(1000));
        String brokerPhoneNumber = "(056) 333-" + String.format("%04d", random.nextInt(10000));
        String agentName = "AutoTestAgent_" + brokerName;
        String agentMail = agentName+ "@mail.com";
        String agentPhoneNumber = "(056) 334" + String.format("%04d", random.nextInt(10000)) + "01";

        //створюємо Broker
        $("#new_broker").shouldBe(visible, Duration.ofSeconds(30)).click();
        $("#brokers-mc_number")
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldBe(enabled)
                .setValue(brokerMcNumber);
        $("#brokers-name").setValue(brokerName);
        $("#brokers-address").setValue("Mountain");
        $("#brokers-city").setValue("Colorado");

        //вкаладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_phone_number").setValue(brokerPhoneNumber);

        //вкладка Agents
        $(byText("Agents")).click();
        $("#agents-0-name").setValue(agentName);
        $("#agents-0-email").setValue(agentMail);
        $("#agents-0-phone_number").setValue(agentPhoneNumber);
        $(".btn-update-broker-agents .btn-primary ").shouldBe(enabled).click();

        //перевіряємо додавання створеного Агента на фреймі New Load під полем Broker
        Thread.sleep(5000);
        $(".bt-load-broker-main-flex").shouldHave(text(brokerName));
        $(".bt-load-broker-main-flex").shouldHave(text("Mountain"));
        $(".bt-load-broker-main-flex").shouldHave(text("Colorado"));
        $(".bt-load-broker-main-flex").shouldHave(text(brokerPhoneNumber));

        //генеруємо дані для редагування Брокера
        $(".broker_buttons .glyphicon-pencil").click();
        String editBrokerMcNumber = "" + random.nextInt(10000000);
        String editBrokerName = "AutoTestBrokerEdit" + String.format("%03d",random.nextInt(1000));
        String editBrokerPhoneNumber = "(056) 333-" + String.format("%04d", random.nextInt(10000));
        String editAgentName = "AutoTestAgentEdit_" + editBrokerName;
        String editAgentMail = editAgentName + "@mail.com";
        String editAgentPhoneNumber = "(056) 334" + String.format("%04d", random.nextInt(10000)) + "01";

        //вводимо відредаговані дані Брокера
        $("#brokers-mc_number")
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldBe(enabled)
                .setValue(editBrokerMcNumber);

//        $("#brokers-mc_number").setValue(editBrokerMcNumber);
        $("#brokers-name").setValue(editBrokerName);
        $("#brokers-address").setValue("Texas");
        $("#brokers-city").setValue("Dallas");

        //вкаладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_phone_number").setValue(editBrokerPhoneNumber);

        //вкладка Agents
        $(byText("Agents")).click();
        $(".edit_agent_btn").click();
        $("#agents-0-name").setValue(editAgentName);
        $("#agents-0-email").setValue(editAgentMail);
        $("#agents-0-phone_number").setValue(editAgentPhoneNumber);
        $(".btn-update-broker-agents .btn-primary ").shouldBe(enabled).click();

        //перевіряємо відредаговані дані брокера
        Thread.sleep(5000);
        $(".bt-load-broker-main-flex").shouldHave(text(editBrokerName));
        $(".bt-load-broker-main-flex").shouldHave(text("Texas"));
        $(".bt-load-broker-main-flex").shouldHave(text("Dallas"));
        $(".bt-load-broker-main-flex").shouldHave(text(editBrokerPhoneNumber));

        //повертаємось на Load bord
        $("#add_load .close").click();
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
