package app.application.services;

import app.domain.model.kafka.RestaurantUpdateMessage;
import app.domain.model.menuplate.MenuDto;
import app.domain.model.letterplate.ProductDto;
import app.domain.ports.input.RestaurantUpdateUseCase;
import app.domain.ports.output.MenuRepository;
import app.domain.ports.output.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantUpdateService implements RestaurantUpdateUseCase {

    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    @Override
    public Mono<Void> updateRestaurantNames(RestaurantUpdateMessage message) {
        // Actualizar menús
        Mono<Void> updateMenus = menuRepository.findByRestaurantIdentifier(message.getIdentifier())
                .flatMap(menu -> {
                    if (!message.getName().equals(menu.getRestaurantname())) {
                        menu.setRestaurantname(message.getName());
                        return menuRepository.save(menu)
                                .doOnSuccess(savedMenu -> log.info("Menu updated - ID: {}, New name: {}",
                                        savedMenu.getIdentifier(), savedMenu.getRestaurantname()));
                    }
                    return Mono.just(menu);
                })
                .then();

        // Actualizar productos
        Mono<Void> updateProducts = productRepository.findByRestaurantIdentifier(message.getIdentifier())
                .flatMap(product -> {
                    if (!message.getName().equals(product.getRestaurantname())) {
                        product.setRestaurantname(message.getName());
                        return productRepository.save(product)
                                .doOnSuccess(savedProduct -> log.info("Product updated - ID: {}, New name: {}",
                                        savedProduct.getIdentifier(), savedProduct.getRestaurantname()));
                    }
                    return Mono.just(product);
                })
                .then();

        // Ejecutar ambas actualizaciones
        return Mono.when(updateMenus, updateProducts);
    }
}
