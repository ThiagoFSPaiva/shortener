package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.core.domain.AccessLog;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import br.com.thiagofspaiva.shortener.core.ports.AccessLogRepository;
import br.com.thiagofspaiva.shortener.core.ports.UrlRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UrlAccessService {

    private final UrlRepository urlRepository;
    private final AccessLogRepository urlAccessRepository;

    public UrlAccessService(UrlRepository urlRepository, AccessLogRepository urlAccessRepository) {
        this.urlRepository = urlRepository;
        this.urlAccessRepository = urlAccessRepository;
    }

    public Optional<ShortenedUrl> getUrlByShortened(String shortUrl) {
        Optional<ShortenedUrl> urlOptional = urlRepository.findByShortUrl(shortUrl);

        urlOptional.ifPresent(url -> {
            LocalDate today = LocalDate.now();
            urlAccessRepository.findAcessLogByDateAndShortenedUrl(url.getId(), today)
                    .ifPresentOrElse(
                            log -> {
                                log.incrementAccessCount();
                                urlAccessRepository.save(log);
                            },
                            () -> urlAccessRepository.save(new AccessLog(url))
                    );
        });

        return urlOptional;
    }
}
