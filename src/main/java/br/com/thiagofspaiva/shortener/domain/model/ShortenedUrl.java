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
    private String shortUrL;
    private LocalDateTime createdAt;
    private int accesCount;

    public ShortenedUrl() {
        this.createdAt = LocalDateTime.now();
    }

    public ShortenedUrl(String originalUrl, String shortUrL) {
        this();
        this.originalUrl = originalUrl;
        this.shortUrL = shortUrL;
        this.accesCount = 0;
    }

    public void incrementAccessCount() {
        this.accesCount++;
    }

}
