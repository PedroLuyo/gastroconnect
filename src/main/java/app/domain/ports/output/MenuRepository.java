package app.domain.ports.output;

import app.domain.model.menuplate.MenuDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuRepository extends ReactiveMongoRepository<MenuDto, String> {
    Mono<MenuDto> findByIdentifier(int identifier);
    Mono<MenuDto> findFirstByOrderByIdentifierDesc();
    Flux<MenuDto> findAllByOrderByIdentifierAsc();
    Flux<MenuDto> findByRestaurantIdentifier(int restaurantIdentifier);
}
