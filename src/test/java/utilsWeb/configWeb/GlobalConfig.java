package utilsWeb.configWeb;

import io.github.cdimascio.dotenv.Dotenv;

public class GlobalConfig {

    public static final String WEB_SITE = System.getenv().getOrDefault("WEB_SITE", "https://preprod.empirenational.com/adm");
    public static String OPTION_LOGIN = "";
    public static String USERNAME = "";
    public static String PASSWORD = "";

    private static final Dotenv dotenv = Dotenv.load();

    public static void setCredentials() {
        switch (OPTION_LOGIN.toLowerCase()) {  // використовується toLowerCase() для уникнення помилки
            case "big": // маленькими літерами
                USERNAME = dotenv.get("BIG_USERNAME1", "defaultBigTruckUser");
                PASSWORD = dotenv.get("BIG_PASSWORD1", "defaultBigTruckPass");
                break;
            case "expedite_disp": // маленькими літерами
            case "expedite":
                USERNAME = dotenv.get("EXP_DISP_USERNAME1", "defaultExpediteUser");
                PASSWORD = dotenv.get("EXP_DISP_PASSWORD1", "defaultExpeditePass");
                break;
            case "expedite_tracker": // маленькими літерами
                USERNAME = dotenv.get("EXP_TRACKER_USERNAME1", "defaultExpediteUser");
                PASSWORD = dotenv.get("EXP_TRACKER_PASSWORD1", "defaultExpeditePass");
                break;
            default:
                throw new IllegalArgumentException("Unknown suite: " + OPTION_LOGIN);
        }
    }
}
