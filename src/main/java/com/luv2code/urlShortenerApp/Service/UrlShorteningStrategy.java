package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;

import java.security.NoSuchAlgorithmException;

public interface UrlShorteningStrategy {
    String shorten(String longUrl) throws Exception;
}
