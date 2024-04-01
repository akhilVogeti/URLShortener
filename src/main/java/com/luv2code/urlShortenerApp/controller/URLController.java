package com.luv2code.urlShortenerApp.controller;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import com.luv2code.urlShortenerApp.Service.UrlShortenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class URLController {

    private UrlShortenService urlShortenService;
    @Autowired
    public URLController(UrlShortenService theUrlShortenService) {
        urlShortenService = theUrlShortenService;
    }

    @PostMapping("/shorten")
    public UrlEntity getShortenedUrl(@RequestBody  UrlEntity urlEntity) {
        System.out.println("under postmapping /shorten");
        UrlEntity theUrlEntity = urlShortenService.shorten(urlEntity.getActualUrl());
        return theUrlEntity;
    }

    @GetMapping("/{shortenedUrl}")
    public UrlEntity getActualUrl(@PathVariable String shortenedUrl) {
         return urlShortenService.findByShortenedUrl(shortenedUrl);
    }

}
