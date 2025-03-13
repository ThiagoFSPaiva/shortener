package br.com.thiagofspaiva.shortener.application.factory;


import br.com.thiagofspaiva.shortener.adapters.outbound.strategy.Base62Shortener;
import br.com.thiagofspaiva.shortener.adapters.outbound.strategy.RandomHashShortener;
import br.com.thiagofspaiva.shortener.domain.enums.ShortenerStrategyType;
import br.com.thiagofspaiva.shortener.domain.ports.ShorteningStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ShortenerFactory {

    private Map<ShortenerStrategyType, Class<? extends ShorteningStrategy>> strategies = Map.of(
            ShortenerStrategyType.RANDOM_HASH, RandomHashShortener.class,
            ShortenerStrategyType.BASE62, Base62Shortener.class
    );

    public ShorteningStrategy getStrategy(ShortenerStrategyType type) {
        try {
            return strategies.get(type).newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid strategy type");
        }
    }
}
