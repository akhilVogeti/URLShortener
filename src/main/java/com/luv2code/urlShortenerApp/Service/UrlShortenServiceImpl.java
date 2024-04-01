package com.luv2code.urlShortenerApp.Service;

import com.luv2code.urlShortenerApp.DAO.URLRepository;
import com.luv2code.urlShortenerApp.Entity.UrlEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.security.SecureRandom;

@Service
public class UrlShortenServiceImpl implements UrlShortenService{


    private URLRepository urlRepository;
    @Autowired
    public UrlShortenServiceImpl(URLRepository theUrlRepository) {
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

//    @Override
//    public void deleteExpired() {
//
//    }

    @Override
    public UrlEntity findByShortenedUrl(String shortUrl) {
        return urlRepository.findByShortenedUrl(shortUrl);
    }

    @Override
    public UrlEntity findByActualUrl(String longUrl) {
        UrlEntity urlEntity = urlRepository.findByActualUrl(longUrl);
        System.out.println("finding the actual url");
        System.out.println(urlEntity);
        return urlEntity;
    }


//    }
    @Override
    public UrlEntity shorten(String longUrl) {
        System.out.println("in shorten service");
        UrlEntity urlEntity = findByActualUrl(longUrl);

            if(urlEntity == null) {
                urlEntity = new UrlEntity();
            }

            String shortUrlGenerated = generateRandomString(10);
            System.out.println("random string done");

            urlEntity.setActualUrl(longUrl);
            urlEntity.setShortUrl(shortUrlGenerated);
            urlEntity.setCreatedAt(LocalDateTime.now());

            System.out.println("url Entity obj assigned values.");
            System.out.println(urlEntity);

            return urlRepository.save(urlEntity);
//
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHAR_LIST.length());
            sb.append(CHAR_LIST.charAt(randomIndex));
        }
        return sb.toString();
    }

}
