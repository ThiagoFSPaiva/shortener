package br.com.thiagofspaiva.shortener.domain.ports;

import br.com.thiagofspaiva.shortener.domain.model.ShortenedUrl;

import java.util.Optional;

public interface UrlRepository {
    ShortenedUrl save(ShortenedUrl shortenedUrl);
    Optional<ShortenedUrl> findShortByUrl(String shortUrl);
}
