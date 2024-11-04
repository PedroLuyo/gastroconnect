package app.domain.ports.input;

import app.domain.model.RestaurantDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RestaurantUseCase {
    Mono<RestaurantDto> createRestaurant(RestaurantDto restaurantDto);
    Mono<RestaurantDto> updateRestaurant(int sequence, RestaurantDto restaurantDto);
    Mono<RestaurantDto> logicalDeleteRestaurant(int sequence);
    Mono<RestaurantDto> restoreRestaurant(int sequence);
    Flux<RestaurantDto> getAllRestaurants();
    Mono<RestaurantDto> getRestaurantBySequence(int sequence);
    Mono<RestaurantDto> getRestaurantByUid(String uid);

}
