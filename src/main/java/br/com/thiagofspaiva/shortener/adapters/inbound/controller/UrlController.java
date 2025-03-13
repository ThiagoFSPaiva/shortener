package br.com.thiagofspaiva.shortener.adapters.inbound.controller;

import br.com.thiagofspaiva.shortener.application.service.UrlAccessService;
import br.com.thiagofspaiva.shortener.application.service.UrlShortenerService;
import br.com.thiagofspaiva.shortener.domain.enums.ShortenerStrategyType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {
    private final UrlShortenerService urlShortenerService;
    private final UrlAccessService urlAccessService;

    public UrlController(UrlShortenerService urlShortenerService, UrlAccessService urlAccessService) {
        this.urlShortenerService = urlShortenerService;
        this.urlAccessService = urlAccessService;
    }

    @GetMapping("/shorten")
    public ResponseEntity<Map<String,String>> shotenUrl(
            @RequestParam String url,
            @RequestParam(defaultValue = "BASE62") ShortenerStrategyType type
    ) {
        String shortUrl = urlShortenerService.shortenUrl(url, type);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("shortUrl", shortUrl));
    }
}
