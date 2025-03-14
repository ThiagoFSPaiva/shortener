package br.com.thiagofspaiva.shortener.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Encurtador de URL")
                        .version("1.0")
                        .description("API para encurtar URLs, obter estatísticas e redirecionar URLs encurtadas.")
                );
    }
}
