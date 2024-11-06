package app.infrastructure.adapters;

import app.domain.model.RestaurantCreateMessage;
import app.domain.model.RestaurantDto;
import app.domain.model.RestaurantUpdateMessage;
import app.domain.ports.output.RestaurantEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantKafkaPublisher implements RestaurantEventPublisher {

    private final KafkaTemplate<String, RestaurantUpdateMessage> updateKafkaTemplate;
    private final KafkaTemplate<String, RestaurantCreateMessage> createKafkaTemplate;

    private static final String UPDATE_TOPIC = "restaurants_updates";
    private static final String CREATE_TOPIC = "restaurants_creates";

    @Override
    public Mono<Void> publishRestaurantUpdate(RestaurantDto restaurantDto) {
        RestaurantUpdateMessage updateMessage = RestaurantUpdateMessage.fromRestaurantDto(restaurantDto);

        return Mono.just(updateMessage)
                .flatMap(message -> Mono.fromFuture(
                        updateKafkaTemplate.send(UPDATE_TOPIC,
                                        String.valueOf(message.getIdentifier()),
                                        message)
                                .toCompletableFuture()
                ))
                .doOnSuccess(result -> log.info("Mensaje de actualización enviado para: {}", updateMessage.getName()))
                .doOnError(error -> log.error("Error al enviar actualización para: {}", updateMessage.getName(), error))
                .then();
    }

    @Override
    public Mono<Void> publishRestaurantCreate(RestaurantDto restaurantDto) {
        RestaurantCreateMessage createMessage = RestaurantCreateMessage.fromRestaurantDto(restaurantDto);

        return Mono.just(createMessage)
                .flatMap(message -> Mono.fromFuture(
                        createKafkaTemplate.send(CREATE_TOPIC,
                                        String.valueOf(message.getIdentifier()),
                                        message)
                                .toCompletableFuture()
                ))
                .doOnSuccess(result -> log.info("Mensaje de creación enviado para: {}", createMessage.getName()))
                .doOnError(error -> log.error("Error al enviar creación para: {}", createMessage.getName(), error))
                .then();
    }
}