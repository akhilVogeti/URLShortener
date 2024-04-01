package com.luv2code.urlShortenerApp.DAO;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository <UrlEntity, Integer> {

    UrlEntity findByShortenedUrl(String shortenedUrl);

    UrlEntity findByActualUrl(String actualUrl);
}
