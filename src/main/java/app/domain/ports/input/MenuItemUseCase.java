package app.domain.ports.input;

import app.domain.model.menuplate.MenuItemDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuItemUseCase {
    Mono<MenuItemDto> createMenuItem(MenuItemDto menuItemDto);
    Mono<MenuItemDto> updateMenuItem(int identifier, MenuItemDto menuItemDto);
    Mono<MenuItemDto> logicalDeleteMenuItem(int identifier);
    Mono<MenuItemDto> restoreMenuItem(int identifier);
    Flux<MenuItemDto> getAllMenuItems();
    Mono<MenuItemDto> getMenuItemByIdentifier(int identifier);
    Flux<MenuItemDto> getMenuItemsByRestaurantIdentifier(int restaurantIdentifier);
}
