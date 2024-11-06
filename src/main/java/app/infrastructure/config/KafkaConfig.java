// infrastructure/config/KafkaConfig.java
package app.infrastructure.config;

import app.domain.model.RestaurantCreateMessage;
import app.domain.model.RestaurantUpdateMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, RestaurantCreateMessage> createKafkaTemplate(
            ProducerFactory<String, RestaurantCreateMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, RestaurantUpdateMessage> updateKafkaTemplate(
            ProducerFactory<String, RestaurantUpdateMessage> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}