package Web.LoadBord;

import Web.Login;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class TestCase13LoadBoard extends Login {

    @Test
    public void newBrocker(){

        //прибрати віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        //відкриваємо New Load
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(20));
        $("#new_load").click();

        //генеруємо дані для створення брокера
        Random random = new Random();
        String brokerMcNumber = "" + random.nextInt(10000000);
        String brokerName = "Auto test broker" + String.format("%02d",random.nextInt(100));
        String brokerPhoneNumber = "(056) 333-" + String.format("%04d", random.nextInt(10000));
        String agentName = "Auto test agent" + String.format("%02d",random.nextInt(100));
        String agentMail = "AutoTestAgent" + String.format("%02d", random.nextInt(100))+ "@mail.com";
        String agentPhoneNumber = "(056) 334" + String.format("%04d", random.nextInt(10000)) + "01";

        //створюємо Broker
        $("#new_broker").click();
        $("#brokers-mc_number").setValue(brokerMcNumber);
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
        $(".btn-update-broker-agents .btn-primary ").click();

        //перевіряємо додавання свтореного Агента на фреймі New Load під полем Broker
        $(".bt-load-broker-main-flex").shouldHave(text(brokerName));
        $(".bt-load-broker-main-flex").shouldHave(text("Mountain"));
        $(".bt-load-broker-main-flex").shouldHave(text("Colorado"));
        $(".bt-load-broker-main-flex").shouldHave(text(brokerPhoneNumber));

        //генеруємо дані для редагування Брокера
        $(".broker_buttons .glyphicon-pencil").click();
        String editBrokerMcNumber = "" + random.nextInt(10000000);
        String editBrokerName = "Auto test broker" + String.format("%02d",random.nextInt(100));
        String editBrokerPhoneNumber = "(056) 333-" + String.format("%04d", random.nextInt(10000));
        String editAgentName = "Auto test agent" + String.format("%02d",random.nextInt(100));
        String editAgentMail = "AutoTestAgent" + String.format("%02d", random.nextInt(100))+ "@mail.com";
        String editAgentPhoneNumber = "(056) 334" + String.format("%04d", random.nextInt(10000)) + "01";

        //вводимо відредаговані дані Брокера
        $("#brokers-mc_number").setValue(editBrokerMcNumber);
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
        $(".btn-update-broker-agents .btn-primary ").click();

        //перевіряємо відредаговані дані брокера
        $(".bt-load-broker-main-flex").shouldHave(text(editBrokerName));
        $(".bt-load-broker-main-flex").shouldHave(text("Texas"));
        $(".bt-load-broker-main-flex").shouldHave(text("Dallas"));
        $(".bt-load-broker-main-flex").shouldHave(text(editBrokerPhoneNumber));

        //повертаємось на Load bord
        $("#add_load .close").click();
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(10)).click();

        System.out.println("TestCase13LoadBoard - OK");
    }
}
