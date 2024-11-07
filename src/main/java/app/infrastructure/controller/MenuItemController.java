package app.infrastructure.controller;

import app.domain.model.menuplate.MenuItemDto;
import app.domain.ports.input.MenuItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuItemController {

    private final MenuItemUseCase menuItemUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<MenuItemDto> createMenuItem(@RequestBody MenuItemDto menuItemDto) {
        return menuItemUseCase.createMenuItem(menuItemDto);
    }

    @GetMapping("/obtain")
    public Flux<MenuItemDto> getAllMenuItems() {
        return menuItemUseCase.getAllMenuItems();
    }

    @GetMapping("/obtain/{identifier}")
    public Mono<MenuItemDto> getMenuItemByIdentifier(@PathVariable int identifier) {
        return menuItemUseCase.getMenuItemByIdentifier(identifier);
    }

    @GetMapping("/obtain/restaurant/{restaurantIdentifier}")
    public Flux<MenuItemDto> getMenuItemsByRestaurantIdentifier(@PathVariable int restaurantIdentifier) {
        return menuItemUseCase.getMenuItemsByRestaurantIdentifier(restaurantIdentifier);
    }

    @PutMapping("/edit/{identifier}")
    public Mono<MenuItemDto> updateMenuItem(
            @PathVariable int identifier,
            @RequestBody MenuItemDto menuItemDto) {
        return menuItemUseCase.updateMenuItem(identifier, menuItemDto);
    }

    @PutMapping("/delete/{identifier}")
    public Mono<MenuItemDto> logicalDeleteMenuItem(@PathVariable int identifier) {
        return menuItemUseCase.logicalDeleteMenuItem(identifier);
    }

    @PutMapping("/restore/{identifier}")
    public Mono<MenuItemDto> restoreMenuItem(@PathVariable int identifier) {
        return menuItemUseCase.restoreMenuItem(identifier);
    }
}
