package app.domain.ports.input;

import app.domain.model.dto.BookingDtos.BookingDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingUseCase {
    Mono<BookingDto> createBooking(BookingDto bookingDto);
    Mono<BookingDto> updateBooking(Integer id, BookingDto bookingDto);
    Mono<BookingDto> getBookingById(Integer id);
    Mono<BookingDto> getBookingByUid(String uid);
    Flux<BookingDto> getAllBookings();
    Flux<BookingDto> getBookingsByRestaurantIdentifier(Integer restaurantIdentifier);
    Mono<BookingDto> logicalDeleteBooking(Integer id);
    Mono<BookingDto> restoreBooking(Integer id);
    Mono<BookingDto> confirmBooking(Integer id);
    Mono<BookingDto> declineBooking(Integer id);
}
