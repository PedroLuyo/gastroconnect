package app.domain.ports.output;

import app.domain.model.letterplate.ProductDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveMongoRepository<ProductDto, String> {
    Mono<ProductDto> findByIdentifier(int identifier);
    Mono<ProductDto> findFirstByOrderByIdentifierDesc();
    Flux<ProductDto> findAllByOrderByIdentifierAsc();
    Flux<ProductDto> findByRestaurantIdentifier(int restaurantIdentifier);
    Flux<ProductDto> findByCategoryIdentifier(int categoryIdentifier);
}
