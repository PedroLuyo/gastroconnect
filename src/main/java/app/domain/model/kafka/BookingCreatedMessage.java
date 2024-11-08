package app.domain.model.kafka;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class BookingCreatedMessage {
    @JsonProperty("identifier")
    private Integer bookingId;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("clientEmail")
    private String clientEmail;

    @JsonProperty("restaurantName")
    private String restaurantName;

    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("reservationTime")
    private LocalTime reservationTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("reservationDate")
    private LocalDate reservationDate;
}
