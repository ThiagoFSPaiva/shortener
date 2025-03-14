package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.application.factory.ShortenerFactory;
import br.com.thiagofspaiva.shortener.core.enums.ShortenerStrategyType;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import br.com.thiagofspaiva.shortener.core.ports.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class UrlShortenerServiceUnitTest {
    @Test
    @DisplayName("Should return shorten url")
    void shoudReturnShortenUrl() {
        UrlRepository urlRepository = Mockito.mock(UrlRepository.class);
        ShortenerFactory factory = Mockito.mock(ShortenerFactory.class);

        Mockito.when(urlRepository.save(any(ShortenedUrl.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.when(factory.getStrategy(ShortenerStrategyType.BASE62)).thenReturn(url -> "abcd1234");

        UrlShortenerService service = new UrlShortenerService(urlRepository, factory);
        String shortUrl = service.shortenUrl("http://thiagao.com", ShortenerStrategyType.BASE62);
        assertNotNull(shortUrl);
    }

}