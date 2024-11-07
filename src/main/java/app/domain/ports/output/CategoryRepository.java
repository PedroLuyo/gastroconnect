package app.domain.ports.output;

import app.domain.model.letterplate.CategoryDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<CategoryDto, String> {
    Mono<CategoryDto> findByIdentifier(int identifier);
    Mono<CategoryDto> findFirstByOrderByIdentifierDesc();
    Flux<CategoryDto> findAllByOrderByIdentifierAsc();
    Flux<CategoryDto> findByRestaurantIdentifier(int restaurantIdentifier);
}
