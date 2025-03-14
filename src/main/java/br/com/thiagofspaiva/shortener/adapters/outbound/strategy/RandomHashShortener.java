package br.com.thiagofspaiva.shortener.adapters.outbound.strategy;

import br.com.thiagofspaiva.shortener.core.ports.ShorteningStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Component
public class RandomHashShortener implements ShorteningStrategy {
    @Override
    public String generateShortUrl(String originalUrl) {
        return DigestUtils.md5DigestAsHex((originalUrl + UUID.randomUUID()).getBytes()).substring(0, 8);
    }
}
