package br.com.thiagofspaiva.shortener.adapters.outbound.strategy;

import br.com.thiagofspaiva.shortener.domain.ports.ShorteningStrategy;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Base62Shortener implements ShorteningStrategy {
    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random random = new Random();

    @Override
    public String generateShortUrl(String originalUrl) {
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
        }
        return sb.toString();
    }
}
