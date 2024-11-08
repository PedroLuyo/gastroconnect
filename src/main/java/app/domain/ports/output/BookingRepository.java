package app.domain.ports.output;

import app.domain.model.entity.BookingEntities.Booking;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingRepository extends ReactiveCrudRepository<Booking, Integer> {
    Mono<Booking> findByUid(String uid);
    Flux<Booking> findByRestaurantIdentifier(Integer restaurantIdentifier);
}
