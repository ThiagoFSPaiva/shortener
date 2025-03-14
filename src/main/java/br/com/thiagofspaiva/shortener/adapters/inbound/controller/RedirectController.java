package br.com.thiagofspaiva.shortener.adapters.inbound.controller;

import br.com.thiagofspaiva.shortener.application.dto.UrlStatisticsDTO;
import br.com.thiagofspaiva.shortener.application.service.UrlAccessService;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
public class RedirectController {
    private final UrlAccessService urlAccessService;

    public RedirectController(UrlAccessService urlAccessService) {
        this.urlAccessService = urlAccessService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirect(@PathVariable String shortUrl) {
        Optional<ShortenedUrl> shortenedUrlModel = urlAccessService.getUrlByShortened(shortUrl);

        if (shortenedUrlModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortenedUrlModel.get().getOriginalUrl())).build();
    }
}
