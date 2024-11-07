package app.domain.ports.output;

import app.domain.model.menuplate.MenuItemDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuItemRepository extends ReactiveMongoRepository<MenuItemDto, String> {
    Mono<MenuItemDto> findByIdentifier(int identifier);
    Mono<MenuItemDto> findFirstByOrderByIdentifierDesc();
    Flux<MenuItemDto> findAllByOrderByIdentifierAsc();
    Flux<MenuItemDto> findByRestaurantIdentifier(int restaurantIdentifier);
}
