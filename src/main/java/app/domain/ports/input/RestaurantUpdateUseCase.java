package app.domain.ports.input;

import app.domain.model.kafka.RestaurantUpdateMessage;
import reactor.core.publisher.Mono;

public interface RestaurantUpdateUseCase {
    Mono<Void> updateRestaurantNames(RestaurantUpdateMessage message);
}
