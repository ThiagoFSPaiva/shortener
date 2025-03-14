package br.com.thiagofspaiva.shortener.core.ports;

import br.com.thiagofspaiva.shortener.core.domain.AccessLog;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;


public interface AccessLogRepository {
    AccessLog save(AccessLog accessLog);
    Optional<AccessLog> findAcessLogByDateAndShortenedUrl(Long shortUrlId, LocalDate date);
    Long getTotalAccessCount(Long shortenedUrlId);
    Long getDistinctAccessDays(Long shortenedUrlId);
}
