package app.domain.ports.output;

import app.domain.model.entity.BookingEntities.Booking;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

// En BookingRepository
public interface BookingRepository extends ReactiveCrudRepository<Booking, Integer> {
    Mono<Booking> findFirstByUid(String uid);  // Nuevo método para un solo resultado
    Flux<Booking> findByUid(String uid);

    Flux<Booking> findByRestaurantIdentifier(Integer restaurantIdentifier);
    Flux<Booking> findByRestaurantRuc(Long restaurantRuc);
}
