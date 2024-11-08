package app.application.services;

import app.domain.model.menuplate.MenuDto;
import app.domain.ports.input.MenuUseCase;
import app.domain.ports.output.MenuItemRepository;
import app.domain.ports.output.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MenuService implements MenuUseCase {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public Mono<MenuDto> createMenu(MenuDto menuDto) {
        return menuRepository.findFirstByOrderByIdentifierDesc()
                .map(lastMenu -> lastMenu.getIdentifier() + 1)
                .defaultIfEmpty(1)
                .flatMap(newIdentifier -> {
                    menuDto.setIdentifier(newIdentifier);
                    menuDto.setStatus(true);
                    return menuRepository.save(menuDto);
                });
    }

    @Override
    public Mono<MenuDto> updateMenu(int identifier, MenuDto menuDto) {
        return menuRepository.findByIdentifier(identifier)
                .flatMap(existingMenu -> {
                    menuDto.setId(existingMenu.getId());
                    menuDto.setIdentifier(identifier);
                    menuDto.setStatus(existingMenu.isStatus());
                    return menuRepository.save(menuDto);
                });
    }

    @Override
    public Mono<MenuDto> logicalDeleteMenu(int identifier) {
        return menuRepository.findByIdentifier(identifier)
                .flatMap(menu -> {
                    if (!menu.isStatus()) {
                        return Mono.just(menu);
                    }
                    menu.setStatus(false);
                    return menuRepository.save(menu);
                });
    }

    @Override
    public Mono<MenuDto> restoreMenu(int identifier) {
        return menuRepository.findByIdentifier(identifier)
                .flatMap(menu -> {
                    if (menu.isStatus()) {
                        return Mono.just(menu);
                    }
                    menu.setStatus(true);
                    return menuRepository.save(menu);
                });
    }

    @Override
    public Flux<MenuDto> getAllMenus() {
        return menuRepository.findAllByOrderByIdentifierAsc()
                .flatMap(menu ->
                        Flux.fromIterable(menu.getMenuItemsIdentifier())
                                .flatMap(menuItemRepository::findByIdentifier)
                                .collectList()
                                .map(menuItems -> {
                                    menu.setMenuItems(menuItems);
                                    return menu;
                                })
                );
    }


    @Override
    public Mono<MenuDto> getMenuByIdentifier(int identifier) {
        return menuRepository.findByIdentifier(identifier)
                .flatMap(menu ->
                        Flux.fromIterable(menu.getMenuItemsIdentifier())
                                .flatMap(menuItemRepository::findByIdentifier)
                                .collectList()
                                .map(menuItems -> {
                                    menu.setMenuItems(menuItems);
                                    return menu;
                                })
                );
    }

    @Override
    public Flux<MenuDto> getMenusByRestaurantIdentifier(int restaurantIdentifier) {
        return menuRepository.findByRestaurantIdentifier(restaurantIdentifier)
                .flatMap(menu ->
                        Flux.fromIterable(menu.getMenuItemsIdentifier())
                                .flatMap(menuItemRepository::findByIdentifier)
                                .collectList()
                                .map(menuItems -> {
                                    menu.setMenuItems(menuItems);
                                    return menu;
                                })
                );
    }


}
