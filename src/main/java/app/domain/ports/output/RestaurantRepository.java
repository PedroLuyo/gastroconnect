package app.domain.ports.output;

import app.domain.model.RestaurantDto;
import reactor.core.publisher.Mono;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RestaurantRepository extends ReactiveMongoRepository<RestaurantDto, String> {
    Mono<RestaurantDto> findBySequence(int sequence);
    Mono<RestaurantDto> findFirstByOrderBySequenceDesc();
    Flux<RestaurantDto> findAllByOrderBySequenceAsc();
    Mono<RestaurantDto> findByUid(String uid);
    Mono<RestaurantDto> findByBusinessInfo_Ruc(Long ruc); // Update this method

}
