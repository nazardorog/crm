package web.bigTruck.smoke.broker;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS019_BrokerEdit {

    // Click Up:
    // CRM SEMI Truck
    // Brockers
    // 2. Редактирование брокера

    // Global data
    String globalMC = GlobalGenerateName.globalMC();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void edit() throws InterruptedException{

        // Login
        GlobalLogin.login("bt_disp1");

        // Data for creating a Broker
        final String mcNumber = globalMC;
        final String dbaName = globalName + "DBA Name 1";
        final String legalName = globalName + "Legal Name 1";
        final String entityType = "BROKER";
        final String address = "Mountain 1";
        final String city = "Colorado 1";
        final String setCreditLimit = "111111";
        final String note = globalName + "Note 1";
        final String mainName = globalName + "Main Name 1";
        final String mail = globalMail;
        final String mainPhoneNumber = globalPhoneNumber;
        final String ffName = globalName + "2nd MC/FF Name 1";
        final String agentName = globalName + "Agent Name 1";
        final String agentLastName = globalName + "Agent Last Name 1";
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

        // Закриває фрейм New load
        $(".modal-new-load-bigtrucks .close").click();
        $(".modal-new-load-bigtrucks").shouldNotBe(visible, Duration.ofSeconds(10));

        // Переходить на список брокерів
        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();

        // Шукає брокера по MC
        $("#brokerssearch-mc_number").setValue(mcNumber).pressEnter();

        // Перевіряє що брокера знайдено
        SelenideElement rowBroker = $$("table.table-brokers-style-profile tbody tr")
                .findBy(text(mcNumber))
                .shouldBe(visible);


        rowBroker.shouldHave(text(legalName));

        // Вибирає редагування брокера
        rowBroker.$(".btn-action-more").click();
        $(".dropdown-profile-style .update_broker_main").click();

        // Перевіряє дані в редагуванні Broker
        $("#update_broker").shouldBe(visible, Duration.ofSeconds(20));
        $("#brokers-mc_number").shouldHave(value(mcNumber));
        $("#brokers-name").shouldHave(value(legalName));
        $("#brokers-dba_name").shouldHave(value(dbaName));
        $("#brokers-entity_type").getSelectedOption().shouldHave(Condition.text(entityType));
        $("#brokers-address").shouldHave(value(address));
        $("#brokers-second_mc_number").shouldHave(value(ffName));
        $("#brokers-city").shouldHave(value(city));
        $("#brokers-custom_limit").shouldHave(value(setCreditLimit));
        $("#brokers-note").shouldHave(text(note));

        // Вкладка Contact перевіряє дані брокера перед редагуванням фрейм Add Broker
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldHave(value (mainName));
        $("#brokers-main_phone_number").shouldHave(value(mainPhoneNumber));
        $("#brokers-main_email").shouldHave(value(mail));

        // Вкладка Agents перевіряє дані брокера перед редагуванням фрейм Add Broker
        $(byText("Agents")).click();
        $("#agents-0-name").shouldHave(value(agentName));
        $("#agents-0-last_name").shouldHave(value(agentLastName));
        $("#agents-0-phone_number").shouldHave(value(globalPhoneNumber + " ex 01"));
        $("#agent-cell_phone-update0").shouldHave(value(globalPhoneNumber + " ex 02"));

        $(byText("General")).click();

        // Data for Edit a Broker
        GlobalGenerateName.globalName();
        final String mcNumberEdit = globalMC;
        final String dbaNameEdit = globalName + "DBA Name 2";
        final String legalNameEdit = globalName + "Legal Name 2";
        final String entityTypeEdit = "BROKER";
        final String addressEdit = "Mountain 2";
        final String cityEdit = "Colorado 2";
        final String setCreditLimitEdit = "111112";
        final String noteEdit = globalName + "Note 2";
        final String mainNameEdit = globalName + "Main Name 2";
        final String mailEdit = globalMail;
        final String mainPhoneNumberEdit = globalPhoneNumber;
        final String ffNameEdit = globalName + "2nd MC/FF Name 2";
        final String agentNameEdit = globalName + "Agent Name 2";
        final String agentLastNameEdit = globalName + "Agent Last Name 2";
        final String agentMailEdit = "AgentMail_" + globalMail;
        final String agentPhoneNumberEdit = globalPhoneNumber + "01";
        final String agentCellPhoneEdit = globalPhoneNumber + "02";

        $("#brokers-dba_name").setValue(dbaNameEdit);
        $("#brokers-name").setValue(legalNameEdit);
        $("#brokers-entity_type").shouldBe(visible).selectOption(entityTypeEdit);
        $("#brokers-second_mc_number").setValue(ffNameEdit);
        $("#brokers-address").setValue(addressEdit);
        $("#brokers-city").setValue(cityEdit);
        $("#brokers-custom_limit").setValue(setCreditLimitEdit);
        $("#brokers-note").setValue(noteEdit);

        // Edit фрейм Add Broker вкладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldBe(visible).setValue(mainNameEdit);
        $("#brokers-main_phone_number").setValue(mainPhoneNumberEdit);
        $("#brokers-main_email").setValue(mailEdit);

        // Edit фрейм Add Broker вкладка Agents
        $(byText("Agents")).click();
        $(".edit_agent_btn").click();
        $("#agents-0-name").setValue(agentNameEdit);
        $("#agents-0-last_name").setValue(agentLastNameEdit);
        $("#agents-0-email").setValue(agentMailEdit);
        $("#agents-0-phone_number").setValue(agentPhoneNumberEdit);
        $("#agent-cell_phone-update0").setValue(agentCellPhoneEdit);

        // Edit фрейм Add Broker приховуємо warning блок
        executeJavaScript("arguments[0].style.display='none';", warningBlock);

        //закриває фрейм Update Broker
        SelenideElement updateModal = $("#update_broker");
        $("#update_broker_send").shouldBe(visible, enabled).click();
        updateModal.shouldNotBe(visible, Duration.ofSeconds(20));

        //шукає брокера по MC
        $("#brokerssearch-mc_number").setValue(mcNumberEdit).pressEnter();

        //перевіряє дані брокера після редагування в списку брокерів
        rowBroker = $$("table.table-brokers-style-profile tbody tr")
                .findBy(text(mcNumber))
                .shouldBe(visible);

        rowBroker.shouldHave(text(legalNameEdit), Duration.ofSeconds(20));

        //вибирає редагування брокера
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        rowBroker.$(".btn-action-more").click();
        $(".dropdown-profile-style .update_broker_main").click();

        //перевіряє дані після редагування в редагуванні Broker
        $("#update_broker").shouldBe(visible, Duration.ofSeconds(20));
        $("#brokers-mc_number").shouldHave(value(mcNumber));
        $("#brokers-name").shouldHave(value(legalNameEdit));
        $("#brokers-dba_name").shouldHave(value(dbaNameEdit));
        $("#brokers-entity_type").getSelectedOption().shouldHave(Condition.text(entityTypeEdit));
        $("#brokers-address").shouldHave(value(addressEdit));
        $("#brokers-second_mc_number").shouldHave(value(ffNameEdit));
        $("#brokers-city").shouldHave(value(cityEdit));
        $("#brokers-custom_limit").shouldHave(value(setCreditLimitEdit));
        $("#brokers-note").shouldHave(text(noteEdit));

        // Перевіряє дані після редагування вкладка Contact перевіряє дані брокера перед редагуванням фрейм Add Broker
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldHave(value(mainNameEdit));
        $("#brokers-main_phone_number").shouldHave(value(mainPhoneNumberEdit));
        $("#brokers-main_email").shouldHave(value(mailEdit));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
