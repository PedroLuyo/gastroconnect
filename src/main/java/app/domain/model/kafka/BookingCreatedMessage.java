package app.domain.model.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class BookingCreatedMessage {
    @JsonProperty("identificadorReserva")
    private Integer bookingId;

    @JsonProperty("nombreCliente")
    private String clientName;

    @JsonProperty("correoCliente")
    private String clientEmail;

    @JsonProperty("nombreRestaurante")
    private String restaurantName;

    @JsonProperty("fechaReserva")
    private LocalDate reservationDate;

    @JsonProperty("horaReserva")
    private LocalTime reservationTime;
}
