package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.DAO.UrlRepository;
import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import com.luv2code.urlShortenerApp.cache.Cache;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
@Log4j2
public class UrlShortenServiceImpl implements UrlShortenService {

    private final Cache<UrlEntity> cache;
    private final HttpSession httpSession;
    private final UrlRepository urlRepository;
    private final UrlShorteningStrategy shortener;

    @Autowired
    public UrlShortenServiceImpl(Cache<UrlEntity> cache, HttpSession httpSession, UrlRepository urlRepository, UrlShorteningStrategy shortener) {
        this.cache = cache;
        this.httpSession = httpSession;
        this.urlRepository = urlRepository;
        this.shortener = shortener;
    }
    @Override
    public List<UrlEntity> findAll() {
        return urlRepository.findAll();
    }

    @Override
    public UrlEntity save(UrlEntity theUrlEntity) {
        return urlRepository.save(theUrlEntity);
    }

    @Override
    @Transactional
    @Scheduled(fixedRate = 3 * 60 * 1000)
    public void cleanUp() {
        LocalDateTime now = LocalDateTime.now();
        log.info("in clean-up method");
        urlRepository.deleteByExpiresAtLessThanEqual(now);
    }

    @Override
    public UrlEntity findByShortenedUrl(String shortUrl) {
        String cacheKey = shortUrl;
        UrlEntity existingUrl = getFromCache(cacheKey);
        if (existingUrl.getCreatedAt() == null) {
            log.info("URL not found in cache, getting from database");
            existingUrl = urlRepository.findByShortenedUrl(shortUrl);

            if (existingUrl.getCreatedAt() != null) {
                log.info("URL found in database, saving to cache");
                saveInCache(cacheKey, existingUrl, 60);
            }

        } else {
            log.info("URL found in cache");
        }

        return existingUrl;
    }


    @Override
    public UrlEntity shorten(String longUrl) {

        String sessionId = httpSession.getId();

        UrlEntity existingUrl = urlRepository.findByActualUrlAndSessionId(longUrl, sessionId);
        if (existingUrl != null && existingUrl.getExpiresAt().isAfter(LocalDateTime.now())) {
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(3);
            log.info("URL already shortened for this session & URL, returning existing record & updating the expiry");
            existingUrl.setExpiresAt(expiry);
            saveInCache(existingUrl.getShortenedUrl(),existingUrl,60);
            return urlRepository.save(existingUrl);
        }

        try {
            String shortUrlGenerated = shortener.shorten(longUrl);
            log.info("Shortened url generated");
            UrlEntity urlEntity = new UrlEntity(longUrl, shortUrlGenerated, LocalDateTime.now(), httpSession.getId());
            saveInCache(shortUrlGenerated,urlEntity,60);
            return urlRepository.save(urlEntity);
        } catch (Exception e) {
            log.error("error in generating shortened string");
            return new UrlEntity();
        }

    }


    private void saveInCache(String shortUrl, UrlEntity entity, long expiry) {
       try {
           cache.save(shortUrl,entity,expiry);
       } catch (Exception e) {
           log.error("error in saveInCache method");
       }
    }



    private UrlEntity getFromCache(String cacheKey) {
       try {
           UrlEntity entity = cache.get(cacheKey);
           return entity;
       } catch (Exception e) {
           log.error("error in getFromCache method");
           return new UrlEntity();
       }
    }
}




