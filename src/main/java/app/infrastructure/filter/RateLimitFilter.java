package app.infrastructure.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicInteger;
import java.time.Duration;
@Component
public class RateLimitFilter implements WebFilter {

    // Control de solicitudes
    private final AtomicInteger requestCount = new AtomicInteger(0);
    private static final int MAX_REQUESTS_PER_MINUTE = 50;
    private static final Duration RESET_PERIOD = Duration.ofMinutes(1);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LocalTime currentTime = LocalTime.now(ZoneId.systemDefault());



        // Control de frecuencia de solicitudes
        if (requestCount.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(
                            "Has excedido el límite de solicitudes. Por favor, intenta nuevamente en un momento."
                                    .getBytes())));
        }

        // Resetear el contador después del período establecido
        Mono.delay(RESET_PERIOD).subscribe(__ -> requestCount.set(0));

        // Agregar headers informativos
        exchange.getResponse().getHeaders().add("X-Restaurant-Hours", "07:00-23:00");
        exchange.getResponse().getHeaders().add("X-Rate-Limit-Remaining",
                String.valueOf(MAX_REQUESTS_PER_MINUTE - requestCount.get()));

        return chain.filter(exchange)
                .doOnError(error -> {
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    exchange.getResponse().getHeaders().add("X-Error-Message",
                            "Hubo un error procesando tu solicitud. Por favor, intenta nuevamente.");
                });
    }


}