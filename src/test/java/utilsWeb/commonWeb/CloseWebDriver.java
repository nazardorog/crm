package utilsWeb.commonWeb;

import com.codeborne.selenide.Selenide;

public class CloseWebDriver {

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println(">>> JVM is shutting down, closing WebDriver...");
            try {
                Selenide.closeWebDriver();
            } catch (Exception ignored) {}
        }));
    }

    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
