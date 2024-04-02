package com.luv2code.urlShortenerApp.DAO;

import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;

public interface UrlRepository extends JpaRepository <UrlEntity, Integer> {

    UrlEntity findByShortenedUrl(String shortenedUrl);

    UrlEntity findByActualUrl(String actualUrl);

    void deleteByExpiresAtLessThanEqual(LocalDateTime expiresAt);

    UrlEntity findByActualUrlAndSessionId(String actualUrl , String sessionId);
}
