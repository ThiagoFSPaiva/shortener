package br.com.thiagofspaiva.shortener.core.ports;

import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;

import java.util.Optional;

public interface UrlRepository {
    ShortenedUrl save(ShortenedUrl shortenedUrl);
    Optional<ShortenedUrl> findByShortUrl(String shortUrl);
}
