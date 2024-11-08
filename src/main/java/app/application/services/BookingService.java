package app.application.services;

import app.application.mapper.BookingMapper;
import app.domain.model.dto.BookingDtos.BookingDto;
import app.domain.model.entity.BookingEntities.*;
import app.domain.ports.input.BookingUseCase;
import app.domain.ports.output.BookingRepository;
import app.domain.ports.output.BookingDetailRepository;
import app.domain.ports.output.BookingMenuDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingService implements BookingUseCase {

    private final BookingRepository bookingRepository;
    private final BookingDetailRepository detailRepository;
    private final BookingMenuDetailRepository menuDetailRepository;
    private final BookingMapper mapper;

    @Override
    public Mono<BookingDto> createBooking(BookingDto bookingDto) {
        return Mono.just(mapper.toEntity(bookingDto))
                .flatMap(bookingRepository::save)
                .flatMap(savedBooking -> Flux.fromIterable(bookingDto.getDetails())
                        .flatMap(detailDto -> {
                            BookingDetail detail = mapper.toDetailEntity(detailDto, savedBooking.getId());
                            return detailRepository.save(detail)
                                    .flatMap(savedDetail -> {
                                        if (Boolean.TRUE.equals(detail.getIsMenu())) {
                                            return Flux.fromIterable(detailDto.getMenuDetails())
                                                    .flatMap(menuDetailDto -> {
                                                        BookingMenuDetail menuDetail = mapper.toMenuDetailEntity(menuDetailDto, savedDetail.getId());
                                                        return menuDetailRepository.save(menuDetail);
                                                    })
                                                    .collectList()
                                                    .thenReturn(savedDetail);
                                        }
                                        return Mono.just(savedDetail);
                                    });
                        })
                        .collectList()
                        .flatMap(savedDetails -> menuDetailRepository.findAll()
                                .collectList()
                                .map(menuDetails -> mapper.toDto(savedBooking, savedDetails, menuDetails))));
    }

    @Override
    public Mono<BookingDto> updateBooking(Integer id, BookingDto bookingDto) {
        return bookingRepository.findById(id)
                .flatMap(existingBooking -> {
                    Booking updatedBooking = mapper.toEntity(bookingDto);
                    updatedBooking.setId(id);
                    return bookingRepository.save(updatedBooking);
                })
                .flatMap(savedBooking ->
                        detailRepository.deleteByBookingId(savedBooking.getId())
                                .then(Mono.just(savedBooking)))
                .flatMap(savedBooking -> createBooking(bookingDto));
    }

    @Override
    public Mono<BookingDto> getBookingById(Integer id) {
        return bookingRepository.findById(id)
                .flatMap(booking ->
                        detailRepository.findByBookingId(booking.getId())
                                .collectList()
                                .flatMap(details ->
                                        menuDetailRepository.findAll()
                                                .collectList()
                                                .map(menuDetails ->
                                                        mapper.toDto(booking, details, menuDetails))));
    }

    @Override
    public Mono<BookingDto> getBookingByUid(String uid) {
        return bookingRepository.findByUid(uid)
                .flatMap(booking ->
                        detailRepository.findByBookingId(booking.getId())
                                .collectList()
                                .flatMap(details ->
                                        menuDetailRepository.findAll()
                                                .collectList()
                                                .map(menuDetails ->
                                                        mapper.toDto(booking, details, menuDetails))));
    }

    @Override
    public Flux<BookingDto> getAllBookings() {
        return bookingRepository.findAll()
                .flatMap(booking ->
                        detailRepository.findByBookingId(booking.getId())
                                .collectList()
                                .flatMap(details ->
                                        menuDetailRepository.findAll()
                                                .collectList()
                                                .map(menuDetails ->
                                                        mapper.toDto(booking, details, menuDetails))));
    }

    @Override
    public Flux<BookingDto> getBookingsByRestaurantIdentifier(Integer restaurantIdentifier) {
        return bookingRepository.findByRestaurantIdentifier(restaurantIdentifier)
                .flatMap(booking ->
                        detailRepository.findByBookingId(booking.getId())
                                .collectList()
                                .flatMap(details ->
                                        menuDetailRepository.findAll()
                                                .collectList()
                                                .map(menuDetails ->
                                                        mapper.toDto(booking, details, menuDetails))));
    }

    @Override
    public Mono<BookingDto> logicalDeleteBooking(Integer id) {
        return bookingRepository.findById(id)
                .flatMap(booking -> {
                    booking.setStatus(false);
                    return bookingRepository.save(booking)
                            .flatMap(savedBooking ->
                                    detailRepository.findByBookingId(savedBooking.getId())
                                            .collectList()
                                            .flatMap(details ->
                                                    menuDetailRepository.findAll()
                                                            .collectList()
                                                            .map(menuDetails ->
                                                                    mapper.toDto(savedBooking, details, menuDetails))));
                });
    }

    @Override
    public Mono<BookingDto> restoreBooking(Integer id) {
        return bookingRepository.findById(id)
                .flatMap(booking -> {
                    booking.setStatus(true);
                    return bookingRepository.save(booking)
                            .flatMap(savedBooking ->
                                    detailRepository.findByBookingId(savedBooking.getId())
                                            .collectList()
                                            .flatMap(details ->
                                                    menuDetailRepository.findAll()
                                                            .collectList()
                                                            .map(menuDetails ->
                                                                    mapper.toDto(savedBooking, details, menuDetails))));
                });
    }

    @Override
    public Mono<BookingDto> confirmBooking(Integer id) {
        return bookingRepository.findById(id)
                .flatMap(booking -> {
                    if ("D".equals(booking.getStage())) {
                        return Mono.error(new IllegalStateException("No se puede confirmar una reserva declinada"));
                    }
                    booking.setStage("C");
                    return bookingRepository.save(booking)
                            .flatMap(savedBooking ->
                                    detailRepository.findByBookingId(savedBooking.getId())
                                            .collectList()
                                            .flatMap(details ->
                                                    menuDetailRepository.findAll()
                                                            .collectList()
                                                            .map(menuDetails ->
                                                                    mapper.toDto(savedBooking, details, menuDetails))));
                });
    }

    @Override
    public Mono<BookingDto> declineBooking(Integer id) {
        return bookingRepository.findById(id)
                .flatMap(booking -> {
                    if ("C".equals(booking.getStage())) {
                        return Mono.error(new IllegalStateException("No se puede declinar una reserva confirmada"));
                    }
                    booking.setStage("D");
                    return bookingRepository.save(booking)
                            .flatMap(savedBooking ->
                                    detailRepository.findByBookingId(savedBooking.getId())
                                            .collectList()
                                            .flatMap(details ->
                                                    menuDetailRepository.findAll()
                                                            .collectList()
                                                            .map(menuDetails ->
                                                                    mapper.toDto(savedBooking, details, menuDetails))));
                });
    }
}
