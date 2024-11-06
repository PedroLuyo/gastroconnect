package app.application.services;

import app.domain.model.RestaurantDto;
import app.domain.ports.input.RestaurantUseCase;
import app.domain.ports.output.RestaurantEventPublisher;
import app.domain.ports.output.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService implements RestaurantUseCase {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantEventPublisher eventPublisher;


    @Override
    public Flux<RestaurantDto> createRestaurants(List<RestaurantDto> restaurantDtos) {
        return restaurantRepository.findFirstByOrderByIdentifierDesc()
                .map(RestaurantDto::getIdentifier)
                .defaultIfEmpty(0)
                .flatMapMany(lastIdentifier -> {
                    int[] identifierCounter = {lastIdentifier};
                    return Flux.fromIterable(restaurantDtos)
                            .flatMap(restaurantDto -> restaurantRepository.findByBusinessInfo_Ruc(restaurantDto.getBusinessInfo().getRuc())
                                    .flatMap(existingRestaurant -> Mono.<RestaurantDto>error(new IllegalArgumentException("RUC already registered")))
                                    .switchIfEmpty(Mono.defer(() -> {
                                        restaurantDto.setIdentifier(++identifierCounter[0]);
                                        restaurantDto.setStatus(true);
                                        return Mono.just(restaurantDto);
                                    }))
                            )
                            .collectList()
                            .flatMapMany(restaurantRepository::saveAll)
                            .flatMap(savedRestaurant ->
                                    eventPublisher.publishRestaurantCreate(savedRestaurant)
                                            .thenReturn(savedRestaurant)
                            );
                });
    }

    @Override
    public Mono<RestaurantDto> updateRestaurant(int identifier, RestaurantDto restaurantDto) {
        return restaurantRepository.findByIdentifier(identifier)
                .flatMap(existingRestaurant -> {
                    restaurantDto.setId(existingRestaurant.getId());
                    restaurantDto.setIdentifier(identifier);
                    return restaurantRepository.save(restaurantDto)
                            .flatMap(savedRestaurant ->
                                    eventPublisher.publishRestaurantUpdate(savedRestaurant)
                                            .thenReturn(savedRestaurant)
                            );
                });
    }

    @Override
    public Mono<RestaurantDto> logicalDeleteRestaurant(int identifier) {
        return restaurantRepository.findByIdentifier(identifier)
                .flatMap(restaurant -> {
                    if (!restaurant.isStatus()) {
                        return Mono.just(restaurant); // Ya está eliminado
                    }
                    restaurant.setStatus(false);
                    return restaurantRepository.save(restaurant);
                });
    }

    @Override
    public Mono<RestaurantDto> restoreRestaurant(int identifier) {
        return restaurantRepository.findByIdentifier(identifier)
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
        return restaurantRepository.findAllByOrderByIdentifierAsc();
    }

    @Override
    public Mono<RestaurantDto> getRestaurantByIdentifier(int identifier) {
        return restaurantRepository.findByIdentifier(identifier);
    }

    @Override
    public Mono<RestaurantDto> getRestaurantByUid(String uid) {
        return restaurantRepository.findByUid(uid);
    }


}
