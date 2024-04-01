package com.luv2code.urlShortenerApp.Service;

import jakarta.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class HashBasedShortener implements UrlShorteningStrategy {

    @Autowired
    private HttpSession httpSession;

    @Override
    public String shorten(String longUrl) throws Exception {
        System.out.println("in hash-based shortening");
        String sessionId = httpSession.getId();
        String text = longUrl + sessionId;
        try {
            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8); // Encode text to byte array

            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(textBytes); // Update digest with byte array
            byte[] md5Hash = md5.digest(); // Generate MD5 hash

//            String base64EncodedHash = Base32.getEncoder().encodeToString(md5Hash); // Base64 encode hash
////            String base62EncodedHash = Base62Codec.INSTANCE.encode(md5Hash);
            String base32Encoded = new Base32().encodeAsString(md5Hash);
            System.out.println("Base64 encoded MD5 hash: " + base32Encoded);
            return base32Encoded;
        } catch (Exception e) {
            System.out.println("error in hash based");
            throw new Exception("internal error");
        }
    }
}











//    byte[] encodedBytes = Base64.getEncoder().encode(combinedString.getBytes());
//    String shortString = new String(encodedBytes);
//        return shortString.substring(0, 7);