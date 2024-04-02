package com.luv2code.urlShortenerApp.Service;

public interface UrlShorteningStrategy {
    String shorten(String longUrl) throws Exception;
}
