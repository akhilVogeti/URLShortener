package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.DAO.UrlRepository;
import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UrlShortenServiceImpl implements UrlShortenService {

    @Autowired
    private HttpSession httpSession;
    private UrlShorteningStrategy shortener;

    @Autowired
    public UrlShortenServiceImpl(UrlShorteningStrategy shortener) {
        this.shortener = shortener;
    }

    public UrlShortenServiceImpl() {
    }


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
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void cleanUp() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("in clean-up method");
        urlRepository.deleteByExpiresAtLessThanEqual(now);
    }

    @Override
    public UrlEntity findByShortenedUrl(String shortUrl) {
        return urlRepository.findByShortenedUrl(shortUrl);
    }

    @Override
    public UrlEntity findByActualUrl(String longUrl) {
        return urlRepository.findByActualUrl(longUrl);
    }

    @Override
    public UrlEntity shorten(String longUrl) throws Exception {
        try {
            String shortUrlGenerated = shortener.shorten(longUrl);
            UrlEntity urlEntity = new UrlEntity(longUrl, shortUrlGenerated, LocalDateTime.now(), httpSession.getId());
            return urlRepository.save(urlEntity);
        } catch (Exception e) {
            System.out.println("internal error");
        }throw new Exception("Internal error");
    }
}




