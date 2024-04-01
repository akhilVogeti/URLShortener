package com.luv2code.urlShortenerApp.Configuration;

import com.luv2code.urlShortenerApp.Service.HashBasedShortener;
import com.luv2code.urlShortenerApp.Service.RandomShortener;
import com.luv2code.urlShortenerApp.Service.UrlShorteningStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {
    @Bean
    @ConditionalOnProperty(name = "shortening.strategy", havingValue = "random")
    public UrlShorteningStrategy randomShortener() {
        return new RandomShortener();
    }

    @Bean
    @ConditionalOnProperty(name = "shortening.strategy", havingValue = "hash-based")
    public UrlShorteningStrategy hashBasedShortener() {
        return new HashBasedShortener();
    }
}
