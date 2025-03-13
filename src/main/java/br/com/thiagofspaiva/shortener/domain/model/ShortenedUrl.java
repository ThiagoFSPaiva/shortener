package br.com.thiagofspaiva.shortener.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;
    private int accesCount;

    public ShortenedUrl() {
        this.createdAt = LocalDateTime.now();
    }

    public ShortenedUrl(String originalUrl, String shortUrl) {
        this();
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
        this.accesCount = 0;
    }

    public void incrementAccessCount() {
        this.accesCount++;
    }

}
