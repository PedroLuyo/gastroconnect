package app.domain.ports.input;

import app.domain.model.letterplate.CategoryDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryUseCase {
    Mono<CategoryDto> createCategory(CategoryDto categoryDto);
    Mono<CategoryDto> updateCategory(int identifier, CategoryDto categoryDto);
    Mono<CategoryDto> logicalDeleteCategory(int identifier);
    Mono<CategoryDto> restoreCategory(int identifier);
    Flux<CategoryDto> getAllCategories();
    Mono<CategoryDto> getCategoryByIdentifier(int identifier);
    Flux<CategoryDto> getCategoriesByRestaurantIdentifier(int restaurantIdentifier);
}