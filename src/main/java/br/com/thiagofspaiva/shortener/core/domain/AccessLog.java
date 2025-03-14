package br.com.thiagofspaiva.shortener.core.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "access_logs")
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ShortenedUrl shortenedUrl;

    private int accesCount;

    private LocalDate accessDate;

    public AccessLog() {
        this.accessDate = LocalDate.now();
    }

    public AccessLog(ShortenedUrl shortenedUrl) {
        this();
        this.shortenedUrl = shortenedUrl;
        this.accesCount = 1;
    }

    public void incrementAccessCount() {
        this.accesCount++;
    }

    public LocalDate getAccessDate() {
        return accessDate;
    }

    public int getAccesCount() {
        return accesCount;
    }
}