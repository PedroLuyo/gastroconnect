package app.infrastructure.controller;

import app.domain.model.letterplate.ProductDto;
import app.domain.ports.input.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductUseCase productUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        return productUseCase.createProduct(productDto);
    }

    @GetMapping("/obtain")
    public Flux<ProductDto> getAllProducts() {
        return productUseCase.getAllProducts();
    }

    @GetMapping("/obtain/{identifier}")
    public Mono<ProductDto> getProductByIdentifier(@PathVariable int identifier) {
        return productUseCase.getProductByIdentifier(identifier);
    }

    @GetMapping("/obtain/restaurant/{restaurantIdentifier}")
    public Flux<ProductDto> getProductsByRestaurantIdentifier(@PathVariable int restaurantIdentifier) {
        return productUseCase.getProductsByRestaurantIdentifier(restaurantIdentifier);
    }

    @GetMapping("/obtain/category/{categoryIdentifier}")
    public Flux<ProductDto> getProductsByCategoryIdentifier(@PathVariable int categoryIdentifier) {
        return productUseCase.getProductsByCategoryIdentifier(categoryIdentifier);
    }

    @PutMapping("/edit/{identifier}")
    public Mono<ProductDto> updateProduct(
            @PathVariable int identifier,
            @RequestBody ProductDto productDto) {
        return productUseCase.updateProduct(identifier, productDto);
    }

    @PutMapping("/delete/{identifier}")
    public Mono<ProductDto> logicalDeleteProduct(@PathVariable int identifier) {
        return productUseCase.logicalDeleteProduct(identifier);
    }

    @PutMapping("/restore/{identifier}")
    public Mono<ProductDto> restoreProduct(@PathVariable int identifier) {
        return productUseCase.restoreProduct(identifier);
    }
}
