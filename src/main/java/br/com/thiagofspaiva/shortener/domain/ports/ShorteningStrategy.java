package br.com.thiagofspaiva.shortener.domain.ports;

public interface ShorteningStrategy {
    String generateShortUrl(String originalUrl);
}
