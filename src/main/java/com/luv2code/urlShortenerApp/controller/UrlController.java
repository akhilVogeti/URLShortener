package com.luv2code.urlShortenerApp.controller;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import com.luv2code.urlShortenerApp.Service.UrlShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shortener")
public class UrlController {

    private UrlShortenService urlShortenService;
    @Autowired
    public UrlController(UrlShortenService theUrlShortenService) {
        urlShortenService = theUrlShortenService;
    }

    @PostMapping("/shorten")
    public UrlEntity getShortenedUrl(@RequestBody  UrlEntity urlEntity) {

        UrlEntity theUrlEntity = null;
        try {
            theUrlEntity = urlShortenService.shorten(urlEntity.getActualUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return theUrlEntity;
    }

    @GetMapping("/expand/{shortenedUrl}")
    public UrlEntity getActualUrl(@PathVariable String shortenedUrl) {
         return urlShortenService.findByShortenedUrl(shortenedUrl);
    }

}
