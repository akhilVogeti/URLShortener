package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class RandomShortener implements UrlShorteningStrategy {
    @Override
    public String shorten(String longUrl) throws Exception{
        System.out.println("in random shortening service");
        String shortUrlGenerated = generateRandomString(10);
        System.out.println("random string done");
        return shortUrlGenerated;
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHAR_LIST.length());
            sb.append(CHAR_LIST.charAt(randomIndex));
        }
        return sb.toString();
    }
}
