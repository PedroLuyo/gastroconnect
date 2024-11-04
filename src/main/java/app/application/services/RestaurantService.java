package app.application.services;

import app.domain.model.RestaurantDto;
import app.domain.ports.input.RestaurantUseCase;
import app.domain.ports.output.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RestaurantService implements RestaurantUseCase {
    private final RestaurantRepository restaurantRepository;

    @Override
    public Mono<RestaurantDto> createRestaurant(RestaurantDto restaurantDto) {
        return restaurantRepository.findByBusinessInfo_Ruc(restaurantDto.getBusinessInfo().getRuc())
                .flatMap(existingRestaurant -> Mono.<RestaurantDto>error(new IllegalArgumentException("RUC already registered")))
                .switchIfEmpty(
                        restaurantRepository.findFirstByOrderBySequenceDesc()
                                .map(lastRestaurant -> lastRestaurant.getSequence() + 1)
                                .defaultIfEmpty(1)
                                .flatMap(newSequence -> {
                                    restaurantDto.setSequence(newSequence);
                                    restaurantDto.setStatus(true);
                                    return restaurantRepository.save(restaurantDto);
                                })
                );
    }

    @Override
    public Mono<RestaurantDto> updateRestaurant(int sequence, RestaurantDto restaurantDto) {
        return restaurantRepository.findBySequence(sequence)
                .flatMap(existingRestaurant -> {
                    restaurantDto.setId(existingRestaurant.getId());
                    restaurantDto.setSequence(sequence);
                    return restaurantRepository.save(restaurantDto);
                });
    }

    @Override
    public Mono<RestaurantDto> logicalDeleteRestaurant(int sequence) {
        return restaurantRepository.findBySequence(sequence)
                .flatMap(restaurant -> {
                    if (!restaurant.isStatus()) {
                        return Mono.just(restaurant); // Ya está eliminado
                    }
                    restaurant.setStatus(false);
                    return restaurantRepository.save(restaurant);
                });
    }

    @Override
    public Mono<RestaurantDto> restoreRestaurant(int sequence) {
        return restaurantRepository.findBySequence(sequence)
                .flatMap(restaurant -> {
                    if (restaurant.isStatus()) {
                        return Mono.just(restaurant); // Ya está activo
                    }
                    restaurant.setStatus(true);
                    return restaurantRepository.save(restaurant);
                });
    }

    @Override
    public Flux<RestaurantDto> getAllRestaurants() {
        return restaurantRepository.findAllByOrderBySequenceAsc();
    }

    @Override
    public Mono<RestaurantDto> getRestaurantBySequence(int sequence) {
        return restaurantRepository.findBySequence(sequence);
    }

    @Override
    public Mono<RestaurantDto> getRestaurantByUid(String uid) {
        return restaurantRepository.findByUid(uid);
    }


}
