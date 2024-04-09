package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.DAO.UrlRepository;
import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import com.luv2code.urlShortenerApp.cache.Cache;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class UrlShortenServiceImpl implements UrlShortenService {

    private static final String REDIS_HOST = "localhost";
    private static final int REDIS_PORT = 6379;
    private static final int EXPIRY_SECONDS = 300;

    private static final JedisPool jedisPool;

    static {  // Static block for initialization
        jedisPool = new JedisPool(REDIS_HOST, REDIS_PORT);
    }


    @Autowired
    private Cache<UrlEntity> cache;

    @Autowired
    private HttpSession httpSession;

    private UrlShorteningStrategy shortener;
    @Autowired
    public UrlShortenServiceImpl(UrlShorteningStrategy shortener) {
        this.shortener = shortener;
    }

    public UrlShortenServiceImpl() {}

    @Autowired
    private UrlRepository urlRepository;
    public UrlShortenServiceImpl(UrlRepository theUrlRepository) {
        urlRepository = theUrlRepository;
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
    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void cleanUp() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("in clean-up method");
        urlRepository.deleteByExpiresAtLessThanEqual(now);
    }

    @Override
    public UrlEntity findByShortenedUrl(String shortUrl) {
        String cacheKey = shortUrl;
        UrlEntity existingUrl = getFromCache(cacheKey);
        if(existingUrl != null) {
            System.out.println("getting short url from cache");
            return existingUrl;
        }
        System.out.println("getting from DB");
        return urlRepository.findByShortenedUrl(shortUrl);
    }

    @Override
    public UrlEntity shorten(String longUrl) {

        String sessionId = httpSession.getId();

        UrlEntity existingUrl = urlRepository.findByActualUrlAndSessionId(longUrl, sessionId);
        if (existingUrl != null && existingUrl.getExpiresAt().isAfter(LocalDateTime.now())) {
            LocalDateTime expiry = LocalDateTime.now().plusMinutes(2);
            System.out.println("URL already shortened for this session & URL, returning existing record & updating the expiry");
            existingUrl.setExpiresAt(expiry);
            saveInCache(existingUrl.getShortenedUrl(),existingUrl,60);
            return urlRepository.save(existingUrl);
        }

        try {
            String shortUrlGenerated = shortener.shorten(longUrl);
            System.out.println("Shortened url generated");
            UrlEntity urlEntity = new UrlEntity(longUrl, shortUrlGenerated, LocalDateTime.now(), httpSession.getId());
            saveInCache(shortUrlGenerated,urlEntity,60);
            return urlRepository.save(urlEntity);
        } catch (Exception e) {
            System.out.println("internal error");
            return null;
        }

    }


    private void saveInCache(String shortUrl, UrlEntity entity, long expiry) {
       try {
           cache.save(shortUrl,entity,expiry);
       } catch (Exception e) {
           System.out.println("error in saveInCache");
       }
    }



    private UrlEntity getFromCache(String cacheKey) {
       try {
           UrlEntity entity = cache.get(cacheKey);
           return entity;
       } catch (Exception e) {
           System.out.println("Error in getFromCache");
           return null;
       }
    }
}




