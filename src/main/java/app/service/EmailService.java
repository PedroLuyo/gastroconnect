package app.service;

import app.dto.BookingMessage;
import app.dto.RestaurantMessage;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateService templateService;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error sending email", e);
        }
    }

    public String getBookingConfirmedTemplate(BookingMessage booking) {
        Map<String, String> variables = new HashMap<>();
        variables.put("clientName", booking.getClientName());
        variables.put("restaurantName", booking.getRestaurantName());
        variables.put("reservationDate", booking.getReservationDate());
        variables.put("reservationTime", booking.getReservationTime());
        variables.put("identifier", booking.getIdentifier().toString());

        return templateService.processTemplate("templates/booking-confirmed.html", variables);
    }

    public String getBookingCreatedTemplate(BookingMessage booking) {
        Map<String, String> variables = new HashMap<>();
        variables.put("clientName", booking.getClientName());
        variables.put("restaurantName", booking.getRestaurantName());
        variables.put("reservationDate", booking.getReservationDate());
        variables.put("reservationTime", booking.getReservationTime());
        variables.put("identifier", booking.getIdentifier().toString());

        return templateService.processTemplate("templates/booking-created.html", variables);
    }

    public String getRestaurantWelcomeTemplate(RestaurantMessage restaurant) {
        Map<String, String> variables = new HashMap<>();
        variables.put("name", restaurant.getName());
        variables.put("businessName", restaurant.getBusinessName());
        variables.put("address", restaurant.getAddress());
        variables.put("phone", restaurant.getPhone().toString());
        variables.put("identifier", restaurant.getIdentifier().toString());

        return templateService.processTemplate("templates/restaurant-welcome.html", variables);
    }
}
