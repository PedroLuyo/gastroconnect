package app.domain.ports.output;

import app.domain.model.RestaurantDto;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RestaurantRepository extends ReactiveMongoRepository<RestaurantDto, String> {
    Mono<RestaurantDto> findByIdentifier(int identifier);
    Mono<RestaurantDto> findFirstByOrderByIdentifierDesc();
    Flux<RestaurantDto> findAllByOrderByIdentifierAsc();
    Mono<RestaurantDto> findByBusinessInfo_Ruc(Long ruc);

    Flux<RestaurantDto> findAllByUid(String uid);  // Este es el método que usaremos
}
