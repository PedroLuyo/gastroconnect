// domain/ports/output/RestaurantEventPublisher.java
package app.domain.ports.output;

import app.domain.model.RestaurantDto;
import reactor.core.publisher.Mono;

public interface RestaurantEventPublisher {
    Mono<Void> publishRestaurantUpdate(RestaurantDto restaurantDto);
    Mono<Void> publishRestaurantCreate(RestaurantDto restaurantDto);
}