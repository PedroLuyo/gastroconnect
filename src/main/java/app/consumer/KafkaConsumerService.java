package app.consumer;

import app.dto.BookingMessage;
import app.dto.RestaurantMessage;
import app.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "bookings_confirmed")
    public void consumeBookingConfirmed(String message) {
        try {
            BookingMessage booking = objectMapper.readValue(message, BookingMessage.class);
            String emailBody = emailService.getBookingConfirmedTemplate(booking);
            emailService.sendEmail(
                    booking.getClientEmail(),
                    "Reserva Confirmada - " + booking.getRestaurantName(),
                    emailBody
            );
            log.info("Processed booking confirmation for ID: {}", booking.getIdentifier());
        } catch (Exception e) {
            log.error("Error processing booking confirmation: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "bookings_created")
    public void consumeBookingCreated(String message) {
        try {
            BookingMessage booking = objectMapper.readValue(message, BookingMessage.class);
            String emailBody = emailService.getBookingCreatedTemplate(booking);
            emailService.sendEmail(
                    booking.getClientEmail(),
                    "Reserva Recibida - " + booking.getRestaurantName(),
                    emailBody
            );
            log.info("Processed booking creation for ID: {}", booking.getIdentifier());
        } catch (Exception e) {
            log.error("Error processing booking creation: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "restaurants_creates")
    public void consumeRestaurantCreated(String message) {
        try {
            RestaurantMessage restaurant = objectMapper.readValue(message, RestaurantMessage.class);
            String emailBody = emailService.getRestaurantWelcomeTemplate(restaurant);
            emailService.sendEmail(
                    restaurant.getEmail(),
                    "¡Bienvenido a GastroConnect! - " + restaurant.getName(),
                    emailBody
            );
            log.info("Processed restaurant creation for ID: {}", restaurant.getIdentifier());
        } catch (Exception e) {
            log.error("Error processing restaurant creation: {}", e.getMessage(), e);
        }
    }
}
