package app.domain.ports.output;

import app.domain.model.entity.BookingEntities;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BookingMenuDetailRepository extends ReactiveCrudRepository<BookingEntities.BookingMenuDetail, Integer> {
    Flux<BookingEntities.BookingMenuDetail> findByBookingDetailId(Integer bookingDetailId);
}
