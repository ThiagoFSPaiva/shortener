package br.com.thiagofspaiva.shortener.application.factory;


import br.com.thiagofspaiva.shortener.adapters.outbound.strategy.Base62Shortener;
import br.com.thiagofspaiva.shortener.adapters.outbound.strategy.RandomHashShortener;
import br.com.thiagofspaiva.shortener.domain.ports.ShorteningStrategy;
import org.springframework.stereotype.Repository;

@Repository
public class ShortenerFactory {

    private final RandomHashShortener randomHashShortener;
    private final Base62Shortener base62Shortener;

    public ShortenerFactory(RandomHashShortener randomHashShortener, Base62Shortener base62Shortener) {
        this.randomHashShortener = randomHashShortener;
        this.base62Shortener = base62Shortener;
    }

    public ShorteningStrategy getStrategy(String type) {
        if (type.equalsIgnoreCase("random")) {
            return randomHashShortener;
        }
        return base62Shortener;
    }
}
