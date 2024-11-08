package app.domain.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;

public class BookingEntities {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("bookings")
    public static class Booking {
        @Id
        private Integer id;
        private String uid;
        private Integer restaurantIdentifier;
        private String restaurantName;
        private Long restaurantRuc;
        private String clientName;
        private String clientEmail;
        private String clientPhone;
        private LocalDate reservationDate;
        private LocalTime reservationTime;
        private BigDecimal totalAmount;
        private Boolean status;
        private LocalDateTime createdAt;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("booking_details")
    public static class BookingDetail {
        @Id
        private Integer id;
        private Integer bookingId;
        private Boolean isMenu;
        private Boolean isProduct;
        private Integer itemIdentifier;
        private String itemName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;
        private Boolean status;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Table("booking_menu_details")
    public static class BookingMenuDetail {
        @Id
        private Integer id;
        private Integer bookingDetailId;
        private Integer menuItemIdentifier;
        private String menuItemName;
        private String menuItemType;
        private Boolean status;
    }
}
