package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
@Component
@Log4j2
@ConditionalOnProperty(name = "shortening.strategy", havingValue = "random")
public class RandomShortener implements UrlShorteningStrategy {

 
    @Override
    public String shorten(String longUrl) throws Exception{
        log.info("in random shortening service");
        String shortUrlGenerated = generateRandomString(10);
        log.info("random string done");
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
