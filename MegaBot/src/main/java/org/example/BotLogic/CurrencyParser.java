package org.example.BotLogic;

import org.example.Structures.Module;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyParser implements Module {
    private String buffer;

    @Override
    public String TakeResult() {
        return buffer;
    }

    @Override
    public void Input(String inp) {
        if (inp.startsWith("/currency")) {
            String currencyCode = inp.substring(9).trim().toUpperCase();
            buffer = getCurrencyRate(currencyCode);
        } else {
            buffer = "Invalid command";
        }
    }

    private String getCurrencyRate(String currencyCode) {
        try {
            String apiUrl = "https://www.cbr-xml-daily.ru/daily_json.js";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();

            String jsonResponse = content.toString();
            int startIndex = jsonResponse.indexOf(currencyCode);
            if (startIndex == -1) {
                return "Currency code not found";
            }
            int valueIndex = jsonResponse.indexOf("Value", startIndex);
            int endIndex = jsonResponse.indexOf(",", valueIndex);
            String rate = jsonResponse.substring(valueIndex + 7, endIndex);
            return "Exchange rate for " + currencyCode + " relative to RUB: " + rate;
        } catch (Exception e) {
            return "Error fetching currency rate: " + e.getMessage();
        }
    }
}
