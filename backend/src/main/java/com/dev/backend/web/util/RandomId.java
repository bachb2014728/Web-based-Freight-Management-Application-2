package com.dev.backend.web.util;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class RandomId {
    public static String randomId(){
        Random random = new Random();
        char letter = (char) ('A' + random.nextInt(26)); // Tạo một ký tự ngẫu nhiên từ A đến Z
        int number = random.nextInt(1000); // Tạo một số ngẫu nhiên từ 000 đến 999
        return String.format("%c%03d", letter, number); // Định dạng chuỗi với ký tự và số
    }
}
