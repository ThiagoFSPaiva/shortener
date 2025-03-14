package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.application.factory.ShortenerFactory;
import br.com.thiagofspaiva.shortener.core.enums.ShortenerStrategyType;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import br.com.thiagofspaiva.shortener.core.ports.UrlRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UrlShortenerServiceTest {
    @Test
    void testShortenUrl() throws InstantiationException, IllegalAccessException {
        UrlRepository urlRepository = Mockito.mock(UrlRepository.class);
        ShortenerFactory factory = Mockito.mock(ShortenerFactory.class);

        when(urlRepository.save(any(ShortenedUrl.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(factory.getStrategy(ShortenerStrategyType.BASE62)).thenReturn(url -> "abcd1234");

        UrlShortenerService service = new UrlShortenerService(urlRepository, factory);
        String shortUrl = service.shortenUrl("http://thiagao.com", ShortenerStrategyType.BASE62);
        assertNotNull(shortUrl);
    }

}