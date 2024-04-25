package utils;

import java.text.DecimalFormat;

public class Converter {
    public static String formatResponseToValue(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }
}
