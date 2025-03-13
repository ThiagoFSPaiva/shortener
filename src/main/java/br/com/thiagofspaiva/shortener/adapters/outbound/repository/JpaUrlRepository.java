package br.com.thiagofspaiva.shortener.adapters.outbound.repository;

import br.com.thiagofspaiva.shortener.domain.model.ShortenedUrl;
import br.com.thiagofspaiva.shortener.domain.ports.UrlRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUrlRepository extends JpaRepository<ShortenedUrl, Long>, UrlRepository {
    Optional<ShortenedUrl> findByShortUrl(String shortUrl);
}
