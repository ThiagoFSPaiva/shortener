package br.com.thiagofspaiva.shortener.adapters.inbound.controller;

import br.com.thiagofspaiva.shortener.application.dto.UrlStatisticsDTO;
import br.com.thiagofspaiva.shortener.application.service.UrlAccessService;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;

@RestController
@Tag(name = "Redirecionamento", description = "Endpoint para redirecionamento de URLs")
public class RedirectController {
    private final UrlAccessService urlAccessService;

    public RedirectController(UrlAccessService urlAccessService) {
        this.urlAccessService = urlAccessService;
    }

    @GetMapping("/{shortUrl}")
    @Operation(
            summary = "Redirecionar para a URL original",
            description = "Redireciona um usuário para a URL original com base na versão encurtada.",
            responses = {
                    @ApiResponse(responseCode = "302", description = "Redirecionamento bem-sucedido"),
                    @ApiResponse(responseCode = "404", description = "URL não encontrada", content = @Content)
            }
    )
    public ResponseEntity<Object> redirect(@PathVariable String shortUrl) {
        Optional<ShortenedUrl> shortenedUrlModel = urlAccessService.getUrlByShortened(shortUrl);

        if (shortenedUrlModel.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(shortenedUrlModel.get().getOriginalUrl())).build();
    }
}