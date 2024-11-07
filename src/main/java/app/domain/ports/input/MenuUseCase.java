package app.domain.ports.input;

import app.domain.model.menuplate.MenuDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MenuUseCase {
    Mono<MenuDto> createMenu(MenuDto menuDto);
    Mono<MenuDto> updateMenu(int identifier, MenuDto menuDto);
    Mono<MenuDto> logicalDeleteMenu(int identifier);
    Mono<MenuDto> restoreMenu(int identifier);
    Flux<MenuDto> getAllMenus();
    Mono<MenuDto> getMenuByIdentifier(int identifier);
    Flux<MenuDto> getMenusByRestaurantIdentifier(int restaurantIdentifier);
}
