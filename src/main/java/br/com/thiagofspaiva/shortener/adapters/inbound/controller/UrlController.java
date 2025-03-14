package br.com.thiagofspaiva.shortener.adapters.inbound.controller;

import br.com.thiagofspaiva.shortener.application.dto.UrlStatisticsDTO;
import br.com.thiagofspaiva.shortener.application.service.UrlAccessService;
import br.com.thiagofspaiva.shortener.application.service.UrlShortenerService;
import br.com.thiagofspaiva.shortener.core.enums.ShortenerStrategyType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/urls")
@Tag(name = "URL Shortener", description = "Endpoints para encurtamento e estatísticas de URLs")
public class UrlController {
    private final UrlShortenerService urlShortenerService;
    private final UrlAccessService urlAccessService;

    public UrlController(UrlShortenerService urlShortenerService, UrlAccessService urlAccessService) {
        this.urlShortenerService = urlShortenerService;
        this.urlAccessService = urlAccessService;
    }

    @PostMapping("/shorten")
    @Operation(
            summary = "Encurtar uma URL",
            description = "Recebe uma URL e retorna a versão encurtada.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "URL encurtada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
            }
    )
    public ResponseEntity<Map<String, String>> shortenUrl(
            @RequestParam String url,
            @RequestParam(defaultValue = "BASE62") ShortenerStrategyType type
    ) {
        String shortUrl = urlShortenerService.shortenUrl(url, type);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("shortUrl", shortUrl));
    }

    @GetMapping("/{shortUrl}/stats")
    @Operation(
            summary = "Obter estatísticas de uma URL encurtada",
            description = "Retorna o número de acessos e outras métricas de uma URL encurtada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Estatísticas encontradas"),
                    @ApiResponse(responseCode = "404", description = "URL não encontrada", content = @Content)
            }
    )
    public ResponseEntity<Object> getUrlStatistics(@PathVariable String shortUrl) {
        Optional<UrlStatisticsDTO> statistics = urlAccessService.getStatistics(shortUrl);

        if (statistics.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("URL not found");
        }
        return ResponseEntity.ok(statistics.get());
    }
}
