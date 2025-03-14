package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.application.factory.ShortenerFactory;
import br.com.thiagofspaiva.shortener.core.enums.ShortenerStrategyType;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import br.com.thiagofspaiva.shortener.core.ports.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {
    @Value("${url_prefix}") String urlPrefix;
    private final UrlRepository urlRepository;
    private final ShortenerFactory shortenerFactory;

    public UrlShortenerService(UrlRepository urlRepository, ShortenerFactory shortenerFactory) {
        this.urlRepository = urlRepository;
        this.shortenerFactory = shortenerFactory;
    }

    public String shortenUrl(String originalUrl, ShortenerStrategyType strategy) {
        String shortUrl = shortenerFactory.getStrategy(strategy).generateShortUrl(originalUrl);
        ShortenedUrl shortenedUrl = new ShortenedUrl(formatUrl(originalUrl), shortUrl);
        urlRepository.save(shortenedUrl);
        return urlPrefix + "/" + shortUrl;
    }

    private String formatUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return "https://" + url;
        }
        return url;
    }
}
