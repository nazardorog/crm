package utilsWeb.configWeb;

import java.util.Random;

public class GlobalGenerateName {
    public static final String GLOBAL_PREFIX = "AT01";
    public static final String GLOBAL_DOMAIN = "@gmail";


    public static String globalName() {

        int randomNumber = (int) (Math.random() * 10000);
        String fourNumber = String.format("%04d", randomNumber);

        return GLOBAL_PREFIX + fourNumber + "_";
    }

    // 7 digits
    public static String globalMC() {

        String beginMcNumber = "10";
        int randomNumber = 10_000 + (int) (Math.random() * 90_000);

        return beginMcNumber + Integer.toString(randomNumber);
    }

    public static String globalPhoneNumber() {

        String codePhone = "001";

        String randomNumberStart = String.format("%03d", (int) (Math.random() * 1000));
        String randomNumber = String.format("%04d", (int) (Math.random() * 10000));

        return "(" + codePhone + ") " + randomNumberStart + "-" + randomNumber;
    }

    public static String globalMail() {

        String randomNumber = String.format("%04d", (int) (Math.random() * 10000));

        return GLOBAL_PREFIX + randomNumber + GLOBAL_DOMAIN + randomNumber + ".com";
    }
}
