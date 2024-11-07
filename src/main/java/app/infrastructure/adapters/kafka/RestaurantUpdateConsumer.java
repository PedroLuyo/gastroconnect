// infrastructure/adapters/kafka/RestaurantUpdateConsumer.java
package app.infrastructure.adapters.kafka;

import app.domain.model.kafka.RestaurantUpdateMessage;
import app.domain.ports.input.RestaurantUpdateUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantUpdateConsumer {

    private final RestaurantUpdateUseCase restaurantUpdateUseCase;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "restaurants_updates",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) {
        try {
            log.info("Received message: {}", message);
            RestaurantUpdateMessage updateMessage = objectMapper.readValue(message, RestaurantUpdateMessage.class);

            log.info("Processing update for restaurant ID: {}, Name: {}",
                    updateMessage.getIdentifier(), updateMessage.getName());

            restaurantUpdateUseCase.updateRestaurantNames(updateMessage)
                    .subscribe(
                            unused -> log.info("Successfully processed update for restaurant ID: {}",
                                    updateMessage.getIdentifier()),
                            throwable -> log.error("Error processing update for restaurant ID: {}, Error: {}",
                                    updateMessage.getIdentifier(), throwable.getMessage())
                    );
        } catch (Exception e) {
            log.error("Error processing message: {}. Message content: {}", e.getMessage(), message);
        }
    }
}