package br.com.thiagofspaiva.shortener.adapters.inbound.controller;

import br.com.thiagofspaiva.shortener.application.service.UrlAccessService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class RedirectController {
    private final UrlAccessService urlAccessService;

    public RedirectController(UrlAccessService urlAccessService) {
        this.urlAccessService = urlAccessService;
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirect(@PathVariable String shortUrl) {
        return urlAccessService.getUrlByShortened(shortUrl)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getOriginalUrl())).build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
