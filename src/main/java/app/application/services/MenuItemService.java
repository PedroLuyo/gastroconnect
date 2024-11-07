package app.application.services;

import app.domain.model.menuplate.MenuItemDto;
import app.domain.ports.input.MenuItemUseCase;
import app.domain.ports.output.MenuItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MenuItemService implements MenuItemUseCase {

    private final MenuItemRepository menuItemRepository;

    @Override
    public Mono<MenuItemDto> createMenuItem(MenuItemDto menuItemDto) {
        return menuItemRepository.findFirstByOrderByIdentifierDesc()
                .map(lastMenuItem -> lastMenuItem.getIdentifier() + 1)
                .defaultIfEmpty(1)
                .flatMap(newIdentifier -> {
                    menuItemDto.setIdentifier(newIdentifier);
                    menuItemDto.setStatus(true);
                    return menuItemRepository.save(menuItemDto);
                });
    }

    @Override
    public Mono<MenuItemDto> updateMenuItem(int identifier, MenuItemDto menuItemDto) {
        return menuItemRepository.findByIdentifier(identifier)
                .flatMap(existingMenuItem -> {
                    menuItemDto.setId(existingMenuItem.getId());
                    menuItemDto.setIdentifier(identifier);
                    menuItemDto.setStatus(existingMenuItem.isStatus());
                    return menuItemRepository.save(menuItemDto);
                });
    }

    @Override
    public Mono<MenuItemDto> logicalDeleteMenuItem(int identifier) {
        return menuItemRepository.findByIdentifier(identifier)
                .flatMap(menuItem -> {
                    if (!menuItem.isStatus()) {
                        return Mono.just(menuItem);
                    }
                    menuItem.setStatus(false);
                    return menuItemRepository.save(menuItem);
                });
    }

    @Override
    public Mono<MenuItemDto> restoreMenuItem(int identifier) {
        return menuItemRepository.findByIdentifier(identifier)
                .flatMap(menuItem -> {
                    if (menuItem.isStatus()) {
                        return Mono.just(menuItem);
                    }
                    menuItem.setStatus(true);
                    return menuItemRepository.save(menuItem);
                });
    }

    @Override
    public Flux<MenuItemDto> getAllMenuItems() {
        return menuItemRepository.findAllByOrderByIdentifierAsc();
    }

    @Override
    public Mono<MenuItemDto> getMenuItemByIdentifier(int identifier) {
        return menuItemRepository.findByIdentifier(identifier);
    }

    @Override
    public Flux<MenuItemDto> getMenuItemsByRestaurantIdentifier(int restaurantIdentifier) {
        return menuItemRepository.findByRestaurantIdentifier(restaurantIdentifier);
    }


}
