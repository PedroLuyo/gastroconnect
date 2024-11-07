package app.application.services;

import app.domain.model.letterplate.ProductDto;
import app.domain.ports.input.ProductUseCase;
import app.domain.ports.output.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;

    @Override
    public Mono<ProductDto> createProduct(ProductDto productDto) {
        return productRepository.findFirstByOrderByIdentifierDesc()
                .map(lastProduct -> lastProduct.getIdentifier() + 1)
                .defaultIfEmpty(1)
                .flatMap(newIdentifier -> {
                    productDto.setIdentifier(newIdentifier);
                    productDto.setStatus(true);
                    return productRepository.save(productDto);
                });
    }

    @Override
    public Mono<ProductDto> updateProduct(int identifier, ProductDto productDto) {
        return productRepository.findByIdentifier(identifier)
                .flatMap(existingProduct -> {
                    productDto.setId(existingProduct.getId());
                    productDto.setIdentifier(identifier);
                    productDto.setStatus(existingProduct.isStatus());
                    return productRepository.save(productDto);
                });
    }

    @Override
    public Mono<ProductDto> logicalDeleteProduct(int identifier) {
        return productRepository.findByIdentifier(identifier)
                .flatMap(product -> {
                    if (!product.isStatus()) {
                        return Mono.just(product);
                    }
                    product.setStatus(false);
                    return productRepository.save(product);
                });
    }

    @Override
    public Mono<ProductDto> restoreProduct(int identifier) {
        return productRepository.findByIdentifier(identifier)
                .flatMap(product -> {
                    if (product.isStatus()) {
                        return Mono.just(product);
                    }
                    product.setStatus(true);
                    return productRepository.save(product);
                });
    }

    @Override
    public Flux<ProductDto> getAllProducts() {
        return productRepository.findAllByOrderByIdentifierAsc();
    }

    @Override
    public Mono<ProductDto> getProductByIdentifier(int identifier) {
        return productRepository.findByIdentifier(identifier);
    }

    @Override
    public Flux<ProductDto> getProductsByRestaurantIdentifier(int restaurantIdentifier) {
        return productRepository.findByRestaurantIdentifier(restaurantIdentifier);
    }

    @Override
    public Flux<ProductDto> getProductsByCategoryIdentifier(int categoryIdentifier) {
        return productRepository.findByCategoryIdentifier(categoryIdentifier);
    }
}
