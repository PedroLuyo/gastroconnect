package app.infrastructure.controller;

import app.domain.model.menuplate.MenuDto;
import app.domain.ports.input.MenuUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuUseCase menuUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MenuDto> createMenu(@RequestBody MenuDto menuDto) {
        return menuUseCase.createMenu(menuDto);
    }

    @GetMapping("/obtain")
    public Flux<MenuDto> getAllMenus() {
        return menuUseCase.getAllMenus();
    }

    @GetMapping("/obtain/{identifier}")
    public Mono<MenuDto> getMenuByIdentifier(@PathVariable int identifier) {
        return menuUseCase.getMenuByIdentifier(identifier);
    }

    @GetMapping("/obtain/restaurant/{restaurantIdentifier}")
    public Flux<MenuDto> getMenusByRestaurantIdentifier(@PathVariable int restaurantIdentifier) {
        return menuUseCase.getMenusByRestaurantIdentifier(restaurantIdentifier);
    }

    @PutMapping("/edit/{identifier}")
    public Mono<MenuDto> updateMenu(
            @PathVariable int identifier,
            @RequestBody MenuDto menuDto) {
        return menuUseCase.updateMenu(identifier, menuDto);
    }

    @PutMapping("/delete/{identifier}")
    public Mono<MenuDto> logicalDeleteMenu(@PathVariable int identifier) {
        return menuUseCase.logicalDeleteMenu(identifier);
    }

    @PutMapping("/restore/{identifier}")
    public Mono<MenuDto> restoreMenu(@PathVariable int identifier) {
        return menuUseCase.restoreMenu(identifier);
    }
}
