package com.luv2code.urlShortenerApp.Service;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base32;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
@Log4j2
@ConditionalOnProperty(name = "shortening.strategy", havingValue = "hash-based")
public class HashBasedShortener implements UrlShorteningStrategy {

    @Autowired
    private HttpSession httpSession;

    @Override
    public String shorten(String longUrl) throws Exception {
        log.info("in hash-based shortening");
        String sessionId = httpSession.getId();
        String text = longUrl + sessionId;
        try {
            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(textBytes);
            byte[] md5Hash = md5.digest();
            String encodedString = new Base32().encodeAsString(md5Hash);
            String shortUrl = randomlySelect8Chars(encodedString);
            return shortUrl;
        } catch (Exception e) {
            log.error("error in hash based shortening");
            throw new Exception();
        }
    }

    private String randomlySelect8Chars(String encodedString) {
        Random random = ThreadLocalRandom.current();
        char[] encodedChars =  encodedString.toCharArray();
        assert encodedChars.length == 21;
        for(int i=20; i >=0; i--) {
            int randomIndex =  random.nextInt(i+1);
            swap(encodedChars,randomIndex,i);
        }

        return new String(encodedChars,0,8);
    }

    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

}










