package api;

import com.google.gson.*;
import model.ExchangeRatePairResponse;
import service.LoggingService;
import utils.Converter;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.io.IOException;

public class ExchangeRateService implements ApiService {
    private String API_KEY;
    private String API_URL = "";
    private LoggingService log;

    public ExchangeRateService() {
        try (InputStream input = new FileInputStream("src/resources/external-resources.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.API_KEY = prop.getProperty("API_KEY");
            this.API_URL = "https://v6.exchangerate-api.com/v6/" + this.API_KEY + "/";
            this.log = new LoggingService();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String findRatesInPair(String firstCurrency, String secondCurrency, String amount) throws IOException, InterruptedException {
        URI fullUrl = URI.create(this.API_URL + "pair/" + firstCurrency + "/" + secondCurrency + "/" + amount);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(fullUrl).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        ExchangeRatePairResponse fullResponse = gson.fromJson(json, ExchangeRatePairResponse.class);

        String stringData = Converter.formatResponseToValue(fullResponse.conversion_result());
        this.buildStringAndLog(firstCurrency, secondCurrency, amount, stringData);
        return stringData;
    }

    @Override
    public String findLastValueOfCurrency(String firstValue) throws IOException, InterruptedException {
        URI fullUrl = URI.create(this.API_URL + "latest/" + firstValue);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(fullUrl).build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        ExchangeRatePairResponse fullResponse = gson.fromJson(json, ExchangeRatePairResponse.class);
        String stringData = Converter.formatResponseToValue(fullResponse.conversion_result());
        return stringData;
    }

    @Override
    public void buildStringAndLog(String firstCurrency, String secondCurrency, String amount, String fullData) throws IOException {
        StringBuilder toLog = new StringBuilder();
        toLog.append("First Currency: ");
        toLog.append(firstCurrency);
        toLog.append("\n");
        toLog.append("Second Currency: ");
        toLog.append(secondCurrency);
        toLog.append("\n");
        toLog.append("Amount: ");
        toLog.append(amount);
        toLog.append("\n");
        toLog.append("API Response: ");
        toLog.append(fullData);

        log.logData(new String(toLog));
    }

}
