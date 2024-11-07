package app.domain.ports.input;

import app.domain.model.letterplate.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<ProductDto> createProduct(ProductDto productDto);
    Mono<ProductDto> updateProduct(int identifier, ProductDto productDto);
    Mono<ProductDto> logicalDeleteProduct(int identifier);
    Mono<ProductDto> restoreProduct(int identifier);
    Flux<ProductDto> getAllProducts();
    Mono<ProductDto> getProductByIdentifier(int identifier);
    Flux<ProductDto> getProductsByRestaurantIdentifier(int restaurantIdentifier);
    Flux<ProductDto> getProductsByCategoryIdentifier(int categoryIdentifier);
}
