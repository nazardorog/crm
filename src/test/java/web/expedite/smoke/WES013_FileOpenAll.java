package web.expedite.smoke;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.LoginHelper;
import utilsWeb.commonWeb.WebDriverConfig;
import utilsWeb.configWeb.GlobalConfig;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WES013_FileOpenAll {

    // Click Up:
    // CRM EXPEDITE
    // Smoke
    // 2. Проверка, что груз создался верно, открываются все типы документов(бол райт кон и тд)

    @Test
    public void openAll() {

        // Login
        GlobalConfig.OPTION_LOGIN = "expedite";
        WebDriverConfig.setup();
        LoginHelper.login();

        $(".logo-mini-icon").shouldBe(enabled, EXPECT_GLOBAL);
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // Дані для створення вантажу
        final String atBroker = "at_Broker1";
        final String atBrokerAgent = "Auto test agent ";
        final String atShippersOrigin = "Auto test shipper 1";
        final String atShippersDist = "Auto test shipper 2";
        final String atReferenceNumber = "10000000";
        final String atCustomersRate = "100000";
        final String atCarrierDriverRate = "80000";
        String atTruck = "0303";

        // Broker
        $("#loads-form-create").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(clickable).click();
        $(".select2-search__field").setValue(atBroker);
        $(".select2-results__options").shouldHave(text(atBroker)).click();
        $$("select#loads-agent_id option").findBy(text(atBrokerAgent)).click();

        // Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue(atShippersOrigin);
        $(".select2-results").shouldHave(text(atShippersOrigin)).click();

        // Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue(atShippersDist);
        $$("li.select2-results__option")
                .findBy(text(atShippersDist))
                .click();

        // Calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        // Calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        // Pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        // Pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        // Input other data
        $("#loads-reference").setValue(atReferenceNumber);
        $("#loads-rate-disp").setValue(atCustomersRate).pressEnter();
        $("#loads-carrier_rate-disp").setValue(atCarrierDriverRate).pressEnter();

        // Download file
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        $("#load_documents_modal").shouldBe(visible, EXPECT_GLOBAL);

        // Файли для завантаження
        File file1 = new File(downloadsFolder + "/1pdf.pdf");
        File file2 = new File(downloadsFolder + "/2pdf.pdf");
        File file3 = new File(downloadsFolder + "/3pdf.pdf");
        File file4 = new File(downloadsFolder + "/4jpeg.jpg");

        // Завантажує чотири файли з різним типом
        // Файл pdf, тип BOL
        $("#loaddocuments-0-file").uploadFile(file1);
        $("#loaddocuments-0-type").selectOption("BOL");
        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        // Файл pdf, тип Rate confirmation
        $$(".add-document").get(1).click();
        $("#loaddocuments-1-file").uploadFile(file2);
        $("#loaddocuments-1-type").selectOption("Rate confirmation");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        // Файл pdf, тип POD
        $$(".add-document").get(1).click();
        $("#loaddocuments-2-file").uploadFile(file3);
        $("#loaddocuments-2-type").selectOption("POD");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        // Файл pdf, тип Others
        $$(".add-document").get(1).click();
        $("#loaddocuments-3-file").uploadFile(file4);
        $("#loaddocuments-3-type").selectOption("Others");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        // Клік по Submit фрейму додавання файлів
        $("#load_documents_modal_pseudo_submit").click();

        // Скрол вниз клік по Submit фрейму додавання вантажу
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").shouldBe(visible).click();

        // Dispatch board
        $("#load_dispatch").shouldBe(visible, EXPECT_GLOBAL);

        // Отримує номер вантажу
        String dispatchLoad = $("#load_dispatch .modal-title").getText();
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        // Вводить Truck
        $("#select2-load_truck_id-0-container")
                .shouldBe(visible, EXPECT_GLOBAL)
                .shouldBe(enabled, EXPECT_GLOBAL)
                .click();
        $(".select2-search__field").setValue(atTruck);
        $(".select2-results__options").shouldHave(text(atTruck)).click();
        $("#select2-load_truck_id-0-container").shouldHave(text(atTruck), EXPECT_GLOBAL);

        // Приховує help блок
        boolean helpBlock = $(".help-block")
                .shouldBe(visible, EXPECT_5)
                .isDisplayed();
        if (helpBlock){
            executeJavaScript("document.querySelector('.help-block').style.display='none'");
        }

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();
        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible, EXPECT_GLOBAL);

        // Тост вспливайка
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Load dispatch sucessfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // Перевіряє коректність доданих файлів в Documents вантажу
        $(".logo-mini-icon").shouldBe(enabled, EXPECT_GLOBAL);
        $(".content-header").shouldHave(text("Load Board"));
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);
        $("td a.view_load").shouldHave(text(loadNumber));

        // Клік три крапки вибір Documents
        $("#main-loads-grid .dropdown-toggle").shouldBe(enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Documents")).shouldBe(enabled).click();
        $("#load_documents").shouldBe(visible, EXPECT_GLOBAL);

        // Перевіряє що доданий перший документ
        $$(".panel-title-address").get(0).shouldHave(Condition.text("Document: 1"));
        $$("input.file-caption-name").get(0).shouldHave(Condition.attribute("title", "1pdf.pdf"));
        $("#loaddocuments-0-description").shouldHave(value("1pdf.pdf"));
        $("#loaddocuments-0-type").getSelectedOption().shouldHave(Condition.text("BOL"));

        // Перевіряє що доданий другий документ
        $$(".panel-title-address").get(1).shouldHave(Condition.text("Document: 2"));
        $$("input.file-caption-name").get(1).shouldHave(Condition.attribute("title", "2pdf.pdf"));
        $("#loaddocuments-1-description").shouldHave(value("2pdf.pdf"));
        $("#loaddocuments-1-type").getSelectedOption().shouldHave(Condition.text("Rate confirmation"));

        // Перевіряє що доданий третій документ
        $$(".panel-title-address").get(2).shouldHave(Condition.text("Document: 3"));
        $$("input.file-caption-name").get(2).shouldHave(Condition.attribute("title", "3pdf.pdf"));
        $("#loaddocuments-2-description").shouldHave(value("3pdf.pdf"));
        $("#loaddocuments-2-type").getSelectedOption().shouldHave(Condition.text("POD"));

        // Перевіряє що доданий четвертий документ
        $$(".panel-title-address").get(3).shouldHave(Condition.text("Document: 4"));
        $$("input.file-caption-name").get(3).shouldHave(Condition.attribute("title", "4jpeg.jpg"));
        $("#loaddocuments-3-description").shouldHave(value("4jpeg.jpg"));
        $("#loaddocuments-3-type").getSelectedOption().shouldHave(Condition.text("Others"));

        // Завантажує 4 файли й перевіряє що вони існують в папці. Попередньо папку очищає
        String fileDownName1 = "downPdf1.pdf";
        String fileDownName2 = "downPdf2.pdf";
        String fileDownName3 = "downPdf3.pdf";
        String fileDownName4 = "downJpeg1.jpg";

        // Селектори для завантаження 4 файлів з лоада
        SelenideElement selectorFile1 = $(".field-loaddocuments-0-file a.kv-file-download");
        SelenideElement selectorFile2 = $(".field-loaddocuments-1-file a.kv-file-download");
        SelenideElement selectorFile3 = $(".field-loaddocuments-2-file a.kv-file-download");
        SelenideElement selectorFile4 = $(".field-loaddocuments-3-file a.kv-file-download");

        // Очищає папку перед завантаженням
        String folderPath = downloadsFolder + "/test-download";
        clearDownloadFolder(folderPath);

        // Завантажує 4 файли й перевіряє що вони з"явились в папці
        download(fileDownName1, selectorFile1);
        download(fileDownName2, selectorFile2);
        download(fileDownName3, selectorFile3);
        download(fileDownName4, selectorFile4);
    }

    public static void download(String fileName, SelenideElement selector) {

        // Завантажує файл
        File downloadedFile = selector.download();

        // Перейменовує файл під потрібне ім"я
        File finalFile = new File(downloadsFolder + "/test-download/" + fileName);
        finalFile.getParentFile().mkdirs();
        downloadedFile.renameTo(finalFile);
        assert finalFile.exists() : "Файл не був завантажений!";

        // Перевіряє, що файл завантажився і існує
        assert finalFile.exists() : "Файл не знайдено!";
        System.out.println("Файл успішно завантажено: " + downloadedFile.getAbsolutePath());

        // Видаляє тимчасову папку
        File tempFolder = downloadedFile.getParentFile();
        tempFolder.delete();
    }

    public static void clearDownloadFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    clearDownloadFolder(file.getAbsolutePath()); // Рекурсивно видаляє підпапки
                }
                file.delete(); // Видаляє файл
            }
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
