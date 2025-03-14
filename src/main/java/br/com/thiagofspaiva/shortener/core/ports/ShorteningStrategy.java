package br.com.thiagofspaiva.shortener.core.ports;

public interface ShorteningStrategy {
    String generateShortUrl(String originalUrl);
}
