package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.application.factory.ShortenerFactory;
import br.com.thiagofspaiva.shortener.domain.enums.ShortenerStrategyType;
import br.com.thiagofspaiva.shortener.domain.model.ShortenedUrl;
import br.com.thiagofspaiva.shortener.domain.ports.UrlRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {
    private final UrlRepository urlRepository;
    private final ShortenerFactory shortenerFactory;

    public UrlShortenerService(UrlRepository urlRepository, ShortenerFactory shortenerFactory) {
        this.urlRepository = urlRepository;
        this.shortenerFactory = shortenerFactory;
    }

    public String shortenUrl(String originalUrl, ShortenerStrategyType strategy) {
        String shortUrl = shortenerFactory.getStrategy(strategy).generateShortUrl(originalUrl);
        ShortenedUrl shortenedUrl = new ShortenedUrl(originalUrl, shortUrl);
        urlRepository.save(shortenedUrl);
        return shortUrl;
    }
}
