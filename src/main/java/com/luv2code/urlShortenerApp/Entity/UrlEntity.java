package com.luv2code.urlShortenerApp.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")
public class UrlEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "actual_url")
    private String actualUrl;
    @Column(name = "shortened_url")
    private String shortenedUrl;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "session_id")
    private String sessionId;

    public UrlEntity() {}

    public UrlEntity(String actualUrl, String shortenedUrl, LocalDateTime createdAt, String sessionId) {
        this.actualUrl = actualUrl;
        this.createdAt = createdAt;
        this.sessionId = sessionId;
        this.shortenedUrl = shortenedUrl;
        this.expiresAt = LocalDateTime.now().plusMinutes(3);

    }

    public UrlEntity(String actualUrl, String shortUrl, LocalDateTime createdAt) {
        this.actualUrl = actualUrl;
        this.createdAt = createdAt;
        this.shortenedUrl = shortUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActualUrl() {
        return actualUrl;
    }

    public void setActualUrl(String actualUrl) {
        this.actualUrl = actualUrl;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortUrl) {
        this.shortenedUrl = shortUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "UrlEntity{" +
                "actualUrl='" + actualUrl + '\'' +
                ", shortUrl='" + shortenedUrl + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                '}';
    }
}
