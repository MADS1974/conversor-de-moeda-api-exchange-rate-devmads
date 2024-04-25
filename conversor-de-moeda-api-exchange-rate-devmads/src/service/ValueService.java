package service;

import api.ExchangeRateService;

import java.io.IOException;

public class ValueService {
    ExchangeRateService service = new ExchangeRateService();

    public String findValueInPairs(String firstCurrency, String secondCurrency, String amount) {
        String valueConverted;

        try {
            valueConverted = service.findRatesInPair(firstCurrency, secondCurrency, amount);
        } catch (IOException |
                 InterruptedException e) {
            throw new RuntimeException(e);
        }
        return valueConverted;
    }

    public String findLastValueOfCurrency(String firstValue) {
        String valueConverted = "";

        try {
            valueConverted = service.findLastValueOfCurrency(firstValue);
        } catch (IOException |
                 InterruptedException e) {
            throw new RuntimeException(e);
        }
        return valueConverted;
    }
}