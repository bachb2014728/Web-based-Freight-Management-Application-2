package com.dev.backend.web.util;

import com.dev.backend.document.Batch;
import org.springframework.context.annotation.Configuration;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
@Configuration
public class FormatUtil {
    public static String formatCurrency(double amount) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(locale);
        return format.format(amount);
    }
    public static String formatWeight(double weightInGrams) {
        return weightInGrams / 1000 + " kg";
    }
    public static String calculateTotalWeight(List<Batch> batches) {
        if(batches.isEmpty()){
            return "0 kg";
        }else{
            return batches.stream().mapToDouble(Batch::getTotalWeight).sum() / 1000 + " kg";
        }

    }
}
