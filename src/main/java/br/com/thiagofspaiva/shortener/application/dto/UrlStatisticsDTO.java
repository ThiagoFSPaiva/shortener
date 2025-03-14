package br.com.thiagofspaiva.shortener.application.dto;

public class UrlStatisticsDTO {
    private final String shortUrl;
    private final long totalAccesses;
    private final double averageAccessesPerDay;

    public UrlStatisticsDTO(String shortUrl, long totalAccesses, double averageAccessesPerDay) {
        this.shortUrl = shortUrl;
        this.totalAccesses = totalAccesses;
        this.averageAccessesPerDay = averageAccessesPerDay;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public long getTotalAccesses() {
        return totalAccesses;
    }

    public double getAverageAccessesPerDay() {
        return averageAccessesPerDay;
    }
}