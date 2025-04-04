package web.LoadBord;

import com.codeborne.selenide.*;
import web.Login;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestCase17LoadBoard extends Login {

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void allFileTypesToShipment() throws InterruptedException {

        System.out.println("TestCase17LoadBoard - Start");

        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        //прибрати віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker2");
        $(".select2-results__options").shouldHave(text("Auto test broker")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent ")).click();

        //Origin Shippers
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(1, 0);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(2, 1);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(3, 2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(4, 3);

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //input other data
        Random random = new Random();
        String reference = String.format("%3d", random.nextInt(10000000)) ;
        $("#loads-reference").setValue(reference);
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        //скролить до низу, клік по завантаженню файлів
        SelenideElement modal = $("#add_load");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        Thread.sleep(3000);

    //load file
        //скролить до верху
        executeJavaScript("arguments[0].scrollTop = 0;", modal);

        //файли для завантажити
        File file1 = new File("C:/Empire/1pdf.pdf");
        File file2 = new File("C:/Empire/2pdf.pdf");
        File file3 = new File("C:/Empire/3pdf.pdf");
        File file4 = new File("C:/Empire/4jpeg.jpg");

        //завантажує чотири файли з різним типом
        //файл pdf, тип BOL
        $("#loaddocuments-0-file").uploadFile(file1);
        $("#loaddocuments-0-type").selectOption("BOL");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //файл pdf, тип Rate confirmation
        $$(".add-document").get(1).click();
        $("#loaddocuments-1-file").uploadFile(file2);
        $("#loaddocuments-1-type").selectOption("Rate confirmation");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //файл pdf, тип POD
        $$(".add-document").get(1).click();
        $("#loaddocuments-2-file").uploadFile(file3);
        $("#loaddocuments-2-type").selectOption("POD");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //файл pdf, тип Others
        $$(".add-document").get(1).click();
        $("#loaddocuments-3-file").uploadFile(file4);
        $("#loaddocuments-3-type").selectOption("Others");
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);

        //клік по Submit фрейму додавання файлів
        $("#load_documents_modal_pseudo_submit").click();

        //скрол вниз клік по Submit фрейму додавання вантажу
        executeJavaScript("arguments[0].scrollTop = arguments[0].scrollHeight;", modal);
        $("#add_load_send_old").shouldBe(visible).click();

    //dispatch board
        $("#select2-load_truck_id-0-container")
                .shouldBe(visible)
                .shouldBe(enabled)
                .click();

        //отримує номер вантажу
        String dispatchLoad = $("#load_dispatch .modal-title").getText();
        String loadNumber = dispatchLoad.substring(dispatchLoad.lastIndexOf("#") + 1).trim();

        //вводить Truck
        $(".select2-search__field").setValue("0303");

        //приховуємо help блок
        SelenideElement helpBlock = $(".help-block");
        executeJavaScript("arguments[0].style.display='none';", helpBlock);

        //перевіряє що Truck вибраний вірно
        $(".select2-results__option--highlighted").shouldHave(text("0303"), Duration.ofSeconds(10)).click();

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();
        $("#dispatch_load_send").click();

        //перевірка в лоад борд коректність доданих файлів
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $(".content-header").shouldHave(text("Load Board"));
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).sendKeys(Keys.ENTER);
        $("td a.view_load").shouldHave(text(loadNumber));

        $("#main-loads-grid .dropdown-toggle").shouldBe(enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Documents")).shouldBe(enabled).click();

        $$(".panel-title-address").get(0).shouldHave(Condition.text("Document: 1"));
        $$("input.file-caption-name").get(0).shouldHave(Condition.attribute("title", "1pdf.pdf"));
        $("#loaddocuments-0-description").shouldHave(value("1pdf.pdf"));
        $("#loaddocuments-0-type").getSelectedOption().shouldHave(Condition.text("BOL"));

        $$(".panel-title-address").get(1).shouldHave(Condition.text("Document: 2"));
        $$("input.file-caption-name").get(1).shouldHave(Condition.attribute("title", "2pdf.pdf"));
        $("#loaddocuments-1-description").shouldHave(value("2pdf.pdf"));
        $("#loaddocuments-1-type").getSelectedOption().shouldHave(Condition.text("Rate confirmation"));

        $$(".panel-title-address").get(2).shouldHave(Condition.text("Document: 3"));
        $$("input.file-caption-name").get(2).shouldHave(Condition.attribute("title", "3pdf.pdf"));
        $("#loaddocuments-2-description").shouldHave(value("3pdf.pdf"));
        $("#loaddocuments-2-type").getSelectedOption().shouldHave(Condition.text("POD"));

        $$(".panel-title-address").get(3).shouldHave(Condition.text("Document: 4"));
        $$("input.file-caption-name").get(3).shouldHave(Condition.attribute("title", "4jpeg.jpg"));
        $("#loaddocuments-3-description").shouldHave(value("4jpeg.jpg"));
        $("#loaddocuments-3-type").getSelectedOption().shouldHave(Condition.text("Others"));

        //завантажує 4 файли й перевіряє що вони існуються в папці. Попередньо папку очищає
        //назви файлів для завантаження
        String fileName1 = "pdf1.pdf";
        String fileName2 = "pdf2.pdf";
        String fileName3 = "pdf3.pdf";
        String fileName4 = "jpeg1.jpg";

        //селектори для завантаження 4 файлів з лоада
        SelenideElement selectorFile1 = $(".field-loaddocuments-0-file a.kv-file-download");
        SelenideElement selectorFile2 = $(".field-loaddocuments-1-file a.kv-file-download");
        SelenideElement selectorFile3 = $(".field-loaddocuments-2-file a.kv-file-download");
        SelenideElement selectorFile4 = $(".field-loaddocuments-3-file a.kv-file-download");

        //очищає папку перед завантаженням
        String folderPath = "C:\\empire\\test-download";
        Configuration.downloadsFolder = folderPath;
        clearDownloadFolder(folderPath);

        //завантажує 4 файли й перевіряє що вони з"явились в папці
        download(fileName1, selectorFile1);
        download(fileName2, selectorFile2);
        download(fileName3, selectorFile3);
        download(fileName4, selectorFile4);

        System.out.println("TestCase17LoadBoard - Test Pass");
    }

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, переключаємо календарь на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        if (switchMonth) {
            Selenide.executeJavaScript("arguments[0].click();", $$(".datetimepicker-days .next").get(numberCalendar));
        }

        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).click();

        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
    }

    public static void download(String fileName, SelenideElement selector) {

        // Завантажує файл
        File downloadedFile = selector.download();

        //перейменовує файл під потрібне ім"я
        File finalFile = new File("C:\\empire\\test-download\\" + fileName);
        downloadedFile.renameTo(finalFile);
        assert finalFile.exists() : "Файл не був завантажений!";

        // Перевіряє, що файл завантажився і існує
        assert finalFile.exists() : "Файл не знайдено!";
        System.out.println("Файл успішно завантажено: " + downloadedFile.getAbsolutePath());
    }

    public static void clearDownloadFolder(String folderPath) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    clearDownloadFolder(file.getAbsolutePath()); // Рекурсивне видалення підпапок
                }
                file.delete(); // Видаляємо файл
            }
        }
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {
        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
