package app.infrastructure.controller;

import app.domain.model.RestaurantDto;
import app.domain.ports.input.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantController {
    private final RestaurantUseCase restaurantUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RestaurantDto> createRestaurant(@RequestBody RestaurantDto restaurantDto) {
        return restaurantUseCase.createRestaurant(restaurantDto);
    }

    @GetMapping("/obtain")
    public Flux<RestaurantDto> getAllRestaurants() {
        return restaurantUseCase.getAllRestaurants();
    }

    @GetMapping("/obtain/{sequence}")
    public Mono<RestaurantDto> getRestaurantBySequence(@PathVariable int sequence) {
        return restaurantUseCase.getRestaurantBySequence(sequence);
    }

    @PutMapping("/edit/{sequence}")
    public Mono<RestaurantDto> updateRestaurant(
            @PathVariable int sequence,
            @RequestBody RestaurantDto restaurantDto) {
        return restaurantUseCase.updateRestaurant(sequence, restaurantDto);
    }

    @GetMapping("/obtain/uid/{uid}")
    public Mono<RestaurantDto> getRestaurantByUid(@PathVariable String uid) {
        return restaurantUseCase.getRestaurantByUid(uid);
    }

    @PutMapping("/delete/{sequence}")
    public Mono<RestaurantDto> logicalDeleteRestaurant(@PathVariable int sequence) {
        return restaurantUseCase.logicalDeleteRestaurant(sequence);
    }

    @PutMapping("/restore/{sequence}")
    public Mono<RestaurantDto> restoreRestaurant(@PathVariable int sequence) {
        return restaurantUseCase.restoreRestaurant(sequence);
    }

}
