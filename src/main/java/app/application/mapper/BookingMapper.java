package app.application.mapper;

import app.domain.model.dto.BookingDtos.*;
import app.domain.model.entity.BookingEntities.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapper {

    public BookingDto toDto(Booking entity, List<BookingDetail> details, List<BookingMenuDetail> menuDetails) {
        return BookingDto.builder()
                .id(entity.getId())
                .uid(entity.getUid())
                .restaurantIdentifier(entity.getRestaurantIdentifier())
                .restaurantName(entity.getRestaurantName())
                .restaurantRuc(entity.getRestaurantRuc())
                .clientName(entity.getClientName())
                .clientEmail(entity.getClientEmail())
                .clientPhone(entity.getClientPhone())
                .reservationDate(entity.getReservationDate())
                .reservationTime(entity.getReservationTime())
                .totalAmount(entity.getTotalAmount())
                .stage(entity.getStage())
                .status(entity.getStatus())
                .details(details.stream()
                        .map(detail -> toDetailDto(detail,
                                menuDetails.stream()
                                        .filter(md -> md.getBookingDetailId().equals(detail.getId()))
                                        .map(this::toMenuDetailDto)
                                        .collect(Collectors.toList())))
                        .collect(Collectors.toList()))
                .build();
    }

    public Booking toEntity(BookingDto dto) {
        return Booking.builder()
                .id(dto.getId())
                .uid(dto.getUid())
                .restaurantIdentifier(dto.getRestaurantIdentifier())
                .restaurantName(dto.getRestaurantName())
                .restaurantRuc(dto.getRestaurantRuc())
                .clientName(dto.getClientName())
                .clientEmail(dto.getClientEmail())
                .clientPhone(dto.getClientPhone())
                .reservationDate(dto.getReservationDate())
                .reservationTime(dto.getReservationTime())
                .totalAmount(dto.getTotalAmount())
                .stage(dto.getStage())
                .status(dto.getStatus())
                .build();
    }

    public BookingDetailDto toDetailDto(BookingDetail entity, List<BookingMenuDetailDto> menuDetails) {
        return BookingDetailDto.builder()
                .id(entity.getId())
                .bookingId(entity.getBookingId())
                .isMenu(entity.getIsMenu())
                .isProduct(entity.getIsProduct())
                .itemIdentifier(entity.getItemIdentifier())
                .itemName(entity.getItemName())
                .quantity(entity.getQuantity())
                .unitPrice(entity.getUnitPrice())
                .subtotal(entity.getSubtotal())
                .menuDetails(menuDetails)
                .status(entity.getStatus())
                .build();
    }

    public BookingDetail toDetailEntity(BookingDetailDto dto, Integer bookingId) {
        return BookingDetail.builder()
                .id(dto.getId())
                .bookingId(bookingId)
                .isMenu(dto.getIsMenu())
                .isProduct(dto.getIsProduct())
                .itemIdentifier(dto.getItemIdentifier())
                .itemName(dto.getItemName())
                .quantity(dto.getQuantity())
                .unitPrice(dto.getUnitPrice())
                .subtotal(dto.getSubtotal())
                .status(dto.getStatus())
                .build();
    }

    public BookingMenuDetailDto toMenuDetailDto(BookingMenuDetail entity) {
        return BookingMenuDetailDto.builder()
                .id(entity.getId())
                .bookingDetailId(entity.getBookingDetailId())
                .menuItemIdentifier(entity.getMenuItemIdentifier())
                .menuItemName(entity.getMenuItemName())
                .menuItemType(entity.getMenuItemType())
                .status(entity.getStatus())
                .build();
    }

    public BookingMenuDetail toMenuDetailEntity(BookingMenuDetailDto dto, Integer bookingDetailId) {
        return BookingMenuDetail.builder()
                .id(dto.getId())
                .bookingDetailId(bookingDetailId)
                .menuItemIdentifier(dto.getMenuItemIdentifier())
                .menuItemName(dto.getMenuItemName())
                .menuItemType(dto.getMenuItemType())
                .status(dto.getStatus())
                .build();
    }
}