package app.infrastructure.controller;

import app.domain.model.dto.BookingDtos.BookingDto;
import app.domain.ports.input.BookingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BookingController {

    private final BookingUseCase bookingUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        return bookingUseCase.createBooking(bookingDto);
    }

    @PutMapping("/edit/{id}")
    public Mono<BookingDto> updateBooking(@PathVariable Integer id, @RequestBody BookingDto bookingDto) {
        return bookingUseCase.updateBooking(id, bookingDto);
    }

    @GetMapping("/obtain/{id}")
    public Mono<BookingDto> getBookingById(@PathVariable Integer id) {
        return bookingUseCase.getBookingById(id);
    }

    @GetMapping("/obtain/uid/{uid}")
    public Mono<BookingDto> getBookingByUid(@PathVariable String uid) {
        return bookingUseCase.getBookingByUid(uid);
    }

    @GetMapping("/obtain")
    public Flux<BookingDto> getAllBookings() {
        return bookingUseCase.getAllBookings();
    }

    @GetMapping("/obtain/restaurant/{restaurantIdentifier}")
    public Flux<BookingDto> getBookingsByRestaurantIdentifier(@PathVariable Integer restaurantIdentifier) {
        return bookingUseCase.getBookingsByRestaurantIdentifier(restaurantIdentifier);
    }

    @PutMapping("/delete/{id}")
    public Mono<BookingDto> logicalDeleteBooking(@PathVariable Integer id) {
        return bookingUseCase.logicalDeleteBooking(id);
    }

    @PutMapping("/restore/{id}")
    public Mono<BookingDto> restoreBooking(@PathVariable Integer id) {
        return bookingUseCase.restoreBooking(id);
    }

    @PutMapping("/confirm/{id}")
    public Mono<BookingDto> confirmBooking(@PathVariable Integer id) {
        return bookingUseCase.confirmBooking(id);
    }

    @PutMapping("/decline/{id}")
    public Mono<BookingDto> declineBooking(@PathVariable Integer id) {
        return bookingUseCase.declineBooking(id);
    }

    @GetMapping("/obtain/client/{uid}")
    public Flux<BookingDto> getBookingsByUid(@PathVariable String uid) {
        return bookingUseCase.getBookingsByUid(uid);
    }

    @GetMapping("/obtain/restaurant/ruc/{ruc}")
    public Flux<BookingDto> getBookingsByRestaurantRuc(@PathVariable Long ruc) {
        return bookingUseCase.getBookingsByRestaurantRuc(ruc);
    }
}
