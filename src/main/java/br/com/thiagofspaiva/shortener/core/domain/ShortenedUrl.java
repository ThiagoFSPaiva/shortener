package br.com.thiagofspaiva.shortener.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "shortened_urls")
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalUrl;
    private String shortUrl;
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "shortenedUrl", fetch = FetchType.LAZY)
    private Set<AccessLog> accessLogs;

    public ShortenedUrl() {
        this.createdAt = LocalDateTime.now();
    }

    public ShortenedUrl(String originalUrl, String shortUrl) {
        this();
        this.originalUrl = formatUrl(originalUrl);
        this.shortUrl = shortUrl;
    }

    public Long getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    private String formatUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }

    public Set<AccessLog> getAccessLogs() {
        return accessLogs;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}