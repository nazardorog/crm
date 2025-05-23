package utilsWeb.commonWeb;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DownloadDocument {

    public static void document(String fileName) throws IOException {

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }

        // Папка завантаження
        String downFolder = Configuration.downloadsFolder;
        String downFolderAfter = Configuration.downloadsFolder + "down/";

        // Отримує class name для зміни назви файлу
        String fileNameAfter = getClassName() + ".pdf";

        // Перевіряє перед завантаженням наявність файлу, якщо є видаляє його
        Path filePath = Paths.get(downFolderAfter + fileNameAfter);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // Завантажує файл
        $("#get_pdf").shouldBe(visible,enabled).click();

        File downloadedPdf = null;
        int attempts = 0;
        int maxAttempts = 20; // 20 * 500мс = 10 секунд
        while (attempts < maxAttempts) {
            try {
                Optional<Path> found = Files.walk(Paths.get(downFolder)) // Пошук завантаженого файлу
                        .filter(p -> p.toString().endsWith(fileName))
                        .findFirst();

                if (found.isPresent()) {
                    Path source = found.get(); // Шлях файлу
                    Path tempFolder = source.getParent(); // Шлях тимчасової папки
                    Path toDir = Paths.get(downFolderAfter); // Шлях папки для переміщення
                    Path file = toDir.resolve(fileNameAfter); // Нове ім"я файлу

                    // Створює папку, якщо не існує
                    Files.createDirectories(toDir);

                    // Переміщує файл
                    Files.move(source, file, StandardCopyOption.REPLACE_EXISTING);

                    // Видаляє тимчасову папку
                    Files.delete(tempFolder);

                    downloadedPdf = file.toFile();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Selenide.sleep(500); // чекаємо 0.5 секунди
            attempts++;
        }

        if (downloadedPdf == null) {
            throw new FileNotFoundException("Файл Load confirmation не був завантажений за 10 секунд");
        }
        else {
            assertThat(downloadedPdf.length()).isGreaterThan(0);
            System.out.println("Файл Load confirmation успішно завантажений");
        }
    }

    public static String getClassName() {

        // Отримує клас зі шляхом і обрізає шлях лишаючи тільки клас
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        if (stackTrace.length >= 4) {

            String fullClassName = stackTrace[3].getClassName();
            return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        }
        return null;
    }
}
