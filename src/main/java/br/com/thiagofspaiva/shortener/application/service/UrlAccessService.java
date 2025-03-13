package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.domain.model.ShortenedUrl;
import br.com.thiagofspaiva.shortener.domain.ports.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UrlAccessService {

    private final UrlRepository urlRepository;

    public UrlAccessService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Optional<ShortenedUrl> getUrlByShortened(String shortUrl) {
       Optional<ShortenedUrl> url = urlRepository.findByShortUrl(shortUrl);
       url.ifPresent(ShortenedUrl::incrementAccessCount);
       url.ifPresent(urlRepository::save);
       return url;
    }

}
