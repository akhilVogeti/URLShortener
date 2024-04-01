package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;

import java.util.List;

public interface UrlShortenService {
    List<UrlEntity> findAll();

    UrlEntity save(UrlEntity theUrlEntity);

//    void deleteExpired();

    UrlEntity findByShortenedUrl(String shortUrl);

    UrlEntity findByActualUrl(String longUrl);

    UrlEntity shorten(String longUrl);
}
