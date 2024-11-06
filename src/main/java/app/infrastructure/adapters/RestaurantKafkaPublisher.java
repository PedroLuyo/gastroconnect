// infrastructure/adapters/RestaurantKafkaPublisher.java
package app.infrastructure.adapters;

import app.domain.model.RestaurantDto;
import app.domain.model.RestaurantUpdateMessage;
import app.domain.ports.output.RestaurantEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RestaurantKafkaPublisher implements RestaurantEventPublisher {

    private final KafkaTemplate<String, RestaurantUpdateMessage> kafkaTemplate;
    private static final String TOPIC = "restaurants_updates";

    @Override
    public Mono<Void> publishRestaurantUpdate(RestaurantDto restaurantDto) {
        RestaurantUpdateMessage updateMessage = RestaurantUpdateMessage.fromRestaurantDto(restaurantDto);

        return Mono.just(updateMessage)
                .flatMap(message -> Mono.fromFuture(
                        kafkaTemplate.send(TOPIC,
                                        String.valueOf(message.getIdentifier()),
                                        message)
                                .toCompletableFuture()
                ))
                .then();
    }
}