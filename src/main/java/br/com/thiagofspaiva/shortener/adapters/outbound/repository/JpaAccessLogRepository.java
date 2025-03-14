package br.com.thiagofspaiva.shortener.adapters.outbound.repository;

import br.com.thiagofspaiva.shortener.core.domain.AccessLog;
import br.com.thiagofspaiva.shortener.core.ports.AccessLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface JpaAccessLogRepository extends JpaRepository<AccessLog, Long>, AccessLogRepository {
    @Query(value = "SELECT * FROM access_logs WHERE shortened_url_id = :shortUrlId AND access_date = :date", nativeQuery = true)
    Optional<AccessLog> findAcessLogByDateAndShortenedUrl(@Param("shortUrlId") Long shortUrlId, @Param("date") LocalDate date);

    @Query(value = "SELECT SUM(acces_count) FROM access_logs WHERE shortened_url_id = :shortenedUrlId", nativeQuery = true)
    Long getTotalAccessCount(@Param("shortenedUrlId") Long shortenedUrlId);

    @Query(value = "SELECT COUNT(DISTINCT access_date) FROM access_logs WHERE shortened_url_id = :shortenedUrlId", nativeQuery = true)
    Long getDistinctAccessDays(@Param("shortenedUrlId") Long shortenedUrlId);
}
