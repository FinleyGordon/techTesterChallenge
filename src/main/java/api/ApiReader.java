package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class ApiReader {

    private static final String ENDPOINT = "http://api.citybik.es/v2/networks";

    private ApiReader () {}

    public static String getCountryOfCity(String city) throws IOException {
        JsonObject jsonObject = readURLAsJsonObject(ENDPOINT + "/" + city);
        JsonObject location = jsonObject.getAsJsonObject("network").getAsJsonObject("location");
        return location.get("country").getAsString();
    }

    private static JsonObject readURLAsJsonObject(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        URLConnection conn = url.openConnection();
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                conn.getInputStream()));
        String inputLine;
        StringBuilder jsonValue = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            jsonValue.append(inputLine);
        }
        Gson gson = new Gson();
        return  gson.fromJson(String.valueOf(jsonValue), JsonObject.class);
    }
}
