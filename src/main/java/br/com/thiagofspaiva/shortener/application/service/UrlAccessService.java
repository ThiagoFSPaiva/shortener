package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.application.dto.UrlStatisticsDTO;
import br.com.thiagofspaiva.shortener.core.domain.AccessLog;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import br.com.thiagofspaiva.shortener.core.ports.AccessLogRepository;
import br.com.thiagofspaiva.shortener.core.ports.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;

@Service
public class UrlAccessService {

    @Value("${url_prefix}") private String urlPrefix;
    private final UrlRepository urlRepository;
    private final AccessLogRepository accessLogRepository;

    public UrlAccessService(UrlRepository urlRepository, AccessLogRepository accessLogRepository) {
        this.urlRepository = urlRepository;
        this.accessLogRepository = accessLogRepository;
    }

    public Optional<ShortenedUrl> getUrlByShortened(String shortUrl) {
        Optional<ShortenedUrl> urlOptional = urlRepository.findByShortUrl(shortUrl);

        urlOptional.ifPresent(url -> {
            LocalDate today = LocalDate.now();
            accessLogRepository.findAcessLogByDateAndShortenedUrl(url.getId(), today)
                    .ifPresentOrElse(
                            log -> {
                                log.incrementAccessCount();
                                accessLogRepository.save(log);
                            },
                            () -> accessLogRepository.save(new AccessLog(url))
                    );
        });

        return urlOptional;
    }

    public Optional<UrlStatisticsDTO> getStatistics(String shortUrl) {
        Optional<ShortenedUrl> urlOptional = urlRepository.findByShortUrl(shortUrl);

        if (urlOptional.isEmpty()) return Optional.empty();

        ShortenedUrl shortenedUrl = urlOptional.get();
        Long totalAccesses = accessLogRepository.getTotalAccessCount(shortenedUrl.getId());
        Long distinctDays = accessLogRepository.getDistinctAccessDays(shortenedUrl.getId());

        if (totalAccesses == null) totalAccesses = 0L;
        if (distinctDays == null || distinctDays == 0) distinctDays = 1L;

        double averageAccessesPerDay = (double) totalAccesses / distinctDays;

        return Optional.of(new UrlStatisticsDTO(urlPrefix + "/" + shortUrl , totalAccesses, averageAccessesPerDay));
    }
}
