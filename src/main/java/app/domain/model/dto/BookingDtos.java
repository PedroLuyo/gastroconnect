package app.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingDtos {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingDto {
        @JsonProperty("identificador")
        private Integer id;

        @JsonProperty("uid")
        private String uid;

        @JsonProperty("identificadorRestaurante")
        private Integer restaurantIdentifier;

        @JsonProperty("nombreRestaurante")
        private String restaurantName;

        @JsonProperty("rucRestaurante")
        private Long restaurantRuc;

        @JsonProperty("nombreCliente")
        private String clientName;

        @JsonProperty("correoCliente")
        private String clientEmail;

        @JsonProperty("telefonoCliente")
        private String clientPhone;

        @JsonProperty("fechaReserva")
        private LocalDate reservationDate;

        @JsonProperty("horaReserva")
        private LocalTime reservationTime;

        @JsonProperty("montoTotal")
        private BigDecimal totalAmount;

        @JsonProperty("etapa")
        private String stage;

        @JsonProperty("detalles")
        private List<BookingDetailDto> details;

        @JsonProperty("estado")
        private Boolean status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingDetailDto {
        @JsonProperty("identificador")
        private Integer id;

        @JsonProperty("identificadorReserva")
        private Integer bookingId;

        @JsonProperty("esMenu")
        private Boolean isMenu;

        @JsonProperty("esProducto")
        private Boolean isProduct;

        @JsonProperty("identificadorItem")
        private Integer itemIdentifier;

        @JsonProperty("nombreItem")
        private String itemName;

        @JsonProperty("cantidad")
        private Integer quantity;

        @JsonProperty("precioUnitario")
        private BigDecimal unitPrice;

        @JsonProperty("subtotal")
        private BigDecimal subtotal;

        @JsonProperty("detallesMenu")
        private List<BookingMenuDetailDto> menuDetails;

        @JsonProperty("estado")
        private Boolean status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookingMenuDetailDto {
        @JsonProperty("identificador")
        private Integer id;

        @JsonProperty("identificadorDetalleReserva")
        private Integer bookingDetailId;

        @JsonProperty("identificadorItemMenu")
        private Integer menuItemIdentifier;

        @JsonProperty("nombreItemMenu")
        private String menuItemName;

        @JsonProperty("tipoItemMenu")
        private String menuItemType;

        @JsonProperty("estado")
        private Boolean status;
    }
}