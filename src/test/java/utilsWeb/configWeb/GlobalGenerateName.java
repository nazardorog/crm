package utilsWeb.configWeb;

public class GlobalGenerateName {

    public static final String GLOBAL_PREFIX_STRING = "AT01_";
    public static final String GLOBAL_PREFIX_INT = "10";
    public static final String GLOBAL_DOMAIN = "@gmail";



    public static String globalName() {

        int randomNumber = (int) (Math.random() * 10000);
        String fourNumber = String.format("%04d", randomNumber);

        return GLOBAL_PREFIX_STRING + fourNumber + "_";
    }

    // 8 digits only letters and digits.
    public static String globalNameLettersDigits() {

        int randomNumber = (int) (Math.random() * 10000);
        String fourNumber = String.format("%04d", randomNumber);

        return GLOBAL_PREFIX_STRING + fourNumber;
    }

    // 7 digits
    public static String globalNumberSeven() {

        int randomNumber = 10_000 + (int) (Math.random() * 90_000);

        return GLOBAL_PREFIX_INT + Integer.toString(randomNumber);
    }

    // 9 digits
    public static String globalNumberNine() {

        int randomNumber = 1_000_000 + (int) (Math.random() * 9_000_000);

        return GLOBAL_PREFIX_INT + Integer.toString(randomNumber);
    }

    public static String globalPhoneNumber() {

        String codePhone = "001";

        String randomNumberStart = String.format("%03d", (int) (Math.random() * 1000));
        String randomNumber = String.format("%04d", (int) (Math.random() * 10000));

        return "(" + codePhone + ") " + randomNumberStart + "-" + randomNumber;
    }

    public static String globalMail() {

        String randomNumber = String.format("%04d", (int) (Math.random() * 10000));

        return GLOBAL_PREFIX_STRING + randomNumber + GLOBAL_DOMAIN + randomNumber + ".com";
    }
}
