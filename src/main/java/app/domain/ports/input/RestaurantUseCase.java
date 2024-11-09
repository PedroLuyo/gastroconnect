package app.domain.ports.input;

import app.domain.model.RestaurantDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RestaurantUseCase {
    Flux<RestaurantDto> createRestaurants(List<RestaurantDto> restaurantDtos);
    Mono<RestaurantDto> updateRestaurant(int identifier, RestaurantDto restaurantDto);
    Mono<RestaurantDto> logicalDeleteRestaurant(int identifier);
    Mono<RestaurantDto> restoreRestaurant(int identifier);
    Flux<RestaurantDto> getAllRestaurants();
    Mono<RestaurantDto> getRestaurantByIdentifier(int identifier);
    Flux<RestaurantDto> getRestaurantByUid(String uid);

}
