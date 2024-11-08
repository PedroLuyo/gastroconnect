package app.infrastructure.adapter;


import app.domain.model.kafka.BookingConfirmedMessage;
import app.domain.model.kafka.BookingCreatedMessage;
import app.domain.ports.output.BookingEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

// infrastructure/adapters/kafka/BookingKafkaPublisher.java
@Component
@RequiredArgsConstructor
public class BookingKafkaPublisher implements BookingEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String CREATED_TOPIC = "bookings_created";
    private static final String CONFIRMED_TOPIC = "bookings_confirmed";

    @Override
    public Mono<Void> publishBookingCreated(BookingCreatedMessage message) {
        return Mono.fromFuture(
                        kafkaTemplate.send(CREATED_TOPIC,
                                        String.valueOf(message.getBookingId()),
                                        message)
                                .toCompletableFuture())
                .then();
    }

    @Override
    public Mono<Void> publishBookingConfirmed(BookingConfirmedMessage message) {
        return Mono.fromFuture(
                        kafkaTemplate.send(CONFIRMED_TOPIC,
                                        String.valueOf(message.getBookingId()),
                                        message)
                                .toCompletableFuture())
                .then();
    }
}
