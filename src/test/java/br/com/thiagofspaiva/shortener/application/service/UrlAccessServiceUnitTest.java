package br.com.thiagofspaiva.shortener.application.service;

import br.com.thiagofspaiva.shortener.application.dto.UrlStatisticsDTO;
import br.com.thiagofspaiva.shortener.core.domain.AccessLog;
import br.com.thiagofspaiva.shortener.core.domain.ShortenedUrl;
import br.com.thiagofspaiva.shortener.core.ports.AccessLogRepository;
import br.com.thiagofspaiva.shortener.core.ports.UrlRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "url_prefix=http://localhost:8080",
})
class UrlAccessServiceUnitTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private AccessLogRepository accessLogRepository;

    @InjectMocks
    private UrlAccessService urlAccessService;

    @Test
    @DisplayName("Should return empty when url is not found")
    void shouldReturnEmptyWhenUrlIsNotFound() {

        // Arrange
        Mockito.when(urlRepository.findByShortUrl(Mockito.any())).thenReturn(Optional.empty());

        // Act
        var result = urlAccessService.getUrlByShortened("url");

        // Assert
        assertTrue(result.isEmpty());
        Mockito.verify(accessLogRepository, Mockito.never()).findAcessLogByDateAndShortenedUrl(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Should return url when url is found")
    void shouldReturnUrlWhenUrlIsFound() {

        var shortenedUrl = new ShortenedUrl("url", "shortened");
        var accessLog = new AccessLog(shortenedUrl);

        Mockito.when(urlRepository.findByShortUrl(Mockito.any())).thenReturn(Optional.of(shortenedUrl));
        Mockito.when(accessLogRepository.findAcessLogByDateAndShortenedUrl(Mockito.any(), Mockito.any())).thenReturn(Optional.of(accessLog));

        var result = urlAccessService.getUrlByShortened("shortened");

        assertTrue(result.isPresent());
        assertEquals(shortenedUrl, result.get());
        assertEquals(2, accessLog.getAccesCount());
        Mockito.verify(accessLogRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Should save and return url")
    void shouldSaveAndReturnUrl() {

        var shortenedUrl = new ShortenedUrl("url", "shortened");
        Mockito.when(urlRepository.findByShortUrl(Mockito.any())).thenReturn(Optional.of(shortenedUrl));
        Mockito.when(accessLogRepository.findAcessLogByDateAndShortenedUrl(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());

        var result = urlAccessService.getUrlByShortened("shortened");

        ArgumentCaptor<AccessLog> accessLogCaptor = ArgumentCaptor.forClass(AccessLog.class);

        assertTrue(result.isPresent());
        assertEquals(shortenedUrl, result.get());
        Mockito.verify(accessLogRepository, Mockito.times(1)).save(accessLogCaptor.capture());
        assertEquals(1, accessLogCaptor.getValue().getAccesCount());
    }

    @Test
    @DisplayName("Should return empty statistic when total access count is not found")
    void ShoudReturnEmptyWhenTotalAccessCountIsNotFound() {

        Mockito.when(urlRepository.findByShortUrl(Mockito.any())).thenReturn(Optional.empty());

        var result = urlAccessService.getStatistics("shortened");

        assertTrue(result.isEmpty());
        Mockito.verify(accessLogRepository, Mockito.never()).getTotalAccessCount(Mockito.any());
        Mockito.verify(accessLogRepository, Mockito.never()).getDistinctAccessDays(Mockito.any());
    }

    @Test
    @DisplayName("Should return empty statistic when total access count is not found")
    void shouldReturnEmptyStatisticWhenTotalAccessCountIsNotFound() {

        var urlPrefix = "http://localhost:8080";
        ReflectionTestUtils.setField(urlAccessService, "urlPrefix", urlPrefix);

        var shortenedUrl = new ShortenedUrl("url", "shortened");
        Mockito.when(urlRepository.findByShortUrl(Mockito.any())).thenReturn(Optional.of(shortenedUrl));

        Mockito.when(accessLogRepository.getTotalAccessCount(Mockito.any())).thenReturn(null);

        var result = urlAccessService.getStatistics("shortened");

        var dto = new UrlStatisticsDTO(urlPrefix + "/" + shortenedUrl.getShortUrl(), 0, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get().getTotalAccesses(), dto.getTotalAccesses());
        assertEquals(result.get().getAverageAccessesPerDay(), dto.getAverageAccessesPerDay());
        assertEquals(result.get().getShortUrl(), dto.getShortUrl());
    }

    @Test
    @DisplayName("Should return statistic")
    void shouldReturnStatistic() {
        var urlPrefix = "http://localhost:8080";
        ReflectionTestUtils.setField(urlAccessService, "urlPrefix", urlPrefix);

        var shortenedUrl = new ShortenedUrl("url", "shortened");
        Mockito.when(urlRepository.findByShortUrl(Mockito.any())).thenReturn(Optional.of(shortenedUrl));

        Mockito.when(accessLogRepository.getTotalAccessCount(Mockito.any())).thenReturn(10L);

        Mockito.when(accessLogRepository.getDistinctAccessDays(Mockito.any())).thenReturn(5L);

        var result = urlAccessService.getStatistics("shortened");

        var dto = new UrlStatisticsDTO(urlPrefix + "/" + shortenedUrl.getShortUrl(), 10, 2);

        assertTrue(result.isPresent());
        assertEquals(result.get().getTotalAccesses(), dto.getTotalAccesses());
        assertEquals(result.get().getAverageAccessesPerDay(), dto.getAverageAccessesPerDay());
        assertEquals(result.get().getShortUrl(), dto.getShortUrl());
    }
}