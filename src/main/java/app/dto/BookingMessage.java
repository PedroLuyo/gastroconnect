package app.dto;

import lombok.Data;

@Data
public class BookingMessage {
    private Long identifier;
    private String clientName;
    private String clientEmail;
    private String restaurantName;
    private String reservationTime;
    private String reservationDate;
}

