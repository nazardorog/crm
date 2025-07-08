package utilsWeb.api.driver;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class driverCredentials {

    private static JSONObject credentials;

    static {
        credentials();
    }

    public static void credentials() {

        try {
            String content = new String(driverCredentials.class.getClassLoader().getResourceAsStream("json/driverCredentials.json").readAllBytes());
            credentials = new JSONObject(content);
        } catch (IOException | JSONException e) {
            throw new RuntimeException("Ошибка загрузки driverCredentials.json: " + e.getMessage());
        }
    }

    public static String getDriverLogin (String driver){
        return credentials.getJSONObject(driver).getString("login");
    }
    
    public static String getDriverPassword (String driver){
        return credentials.getJSONObject(driver).getString("password");
    }
}
