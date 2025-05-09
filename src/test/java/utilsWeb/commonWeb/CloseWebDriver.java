package utilsWeb.commonWeb;

import com.codeborne.selenide.Selenide;

public class CloseWebDriver {

    public static void tearDown() {
        Selenide.closeWebDriver();
    }
}
