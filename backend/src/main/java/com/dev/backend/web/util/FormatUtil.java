package com.dev.backend.web.util;

import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.util.Locale;
@Configuration
public class FormatUtil {
    public static String formatCurrency(double amount) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(amount);
    }
    public static String formatWeight(double weightInGrams) {
        if (weightInGrams < 1000) {
            return weightInGrams + " gram";
        } else {
            return weightInGrams / 1000 + " kg";
        }
    }
}
