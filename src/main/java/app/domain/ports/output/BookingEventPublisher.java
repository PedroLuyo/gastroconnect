package app.domain.ports.output;

import app.domain.model.kafka.BookingConfirmedMessage;
import app.domain.model.kafka.BookingCreatedMessage;
import reactor.core.publisher.Mono;

// domain/ports/output/BookingEventPublisher.java
public interface BookingEventPublisher {
    Mono<Void> publishBookingCreated(BookingCreatedMessage message);
    Mono<Void> publishBookingConfirmed(BookingConfirmedMessage message);
}
