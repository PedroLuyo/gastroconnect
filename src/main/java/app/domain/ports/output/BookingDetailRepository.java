package app.domain.ports.output;

import app.domain.model.entity.BookingEntities;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingDetailRepository extends ReactiveCrudRepository<BookingEntities.BookingDetail, Integer> {
    Flux<BookingEntities.BookingDetail> findByBookingId(Integer bookingId);
    Mono<Void> deleteByBookingId(Integer bookingId);  // Agregado este método

}
