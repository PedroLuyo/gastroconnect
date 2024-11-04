package app.infrastructure.config;

import app.infrastructure.filter.RateLimitFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class WebConfig {

    @Bean
    @Primary
    public RateLimitFilter rateLimitFilter() {
        return new RateLimitFilter();
    }
}