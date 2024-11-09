package app.infrastructure.controller;

import app.domain.model.RestaurantDto;
import app.domain.ports.input.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantController {
    private final RestaurantUseCase restaurantUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<RestaurantDto> createRestaurants(@RequestBody List<RestaurantDto> restaurantDtos) {
        return restaurantUseCase.createRestaurants(restaurantDtos);
    }

    @GetMapping("/obtain")
    public Flux<RestaurantDto> getAllRestaurants() {
        return restaurantUseCase.getAllRestaurants();
    }

    @GetMapping("/obtain/{identifier}")
    public Mono<RestaurantDto> getRestaurantByIdentifier(@PathVariable int identifier) {
        return restaurantUseCase.getRestaurantByIdentifier(identifier);
    }

    @PutMapping("/edit/{sequence}")
    public Mono<RestaurantDto> updateRestaurant(
            @PathVariable int sequence,
            @RequestBody RestaurantDto restaurantDto) {
        return restaurantUseCase.updateRestaurant(sequence, restaurantDto);
    }

    @GetMapping("/obtain/uid/{uid}")
    public Flux<RestaurantDto> getRestaurantByUid(@PathVariable String uid) {
        return restaurantUseCase.getRestaurantByUid(uid);
    }


    @PutMapping("/delete/{sequence}")
    public Mono<RestaurantDto> logicalDeleteRestaurant(@PathVariable int sequence) {
        return restaurantUseCase.logicalDeleteRestaurant(sequence);
    }

    @PutMapping("/restore/{identifier}")
    public Mono<RestaurantDto> restoreRestaurant(@PathVariable int identifier) {
        return restaurantUseCase.restoreRestaurant(identifier);
    }
}
