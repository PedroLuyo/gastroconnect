package app.application.services;

import app.domain.model.letterplate.CategoryDto;
import app.domain.ports.input.CategoryUseCase;
import app.domain.ports.output.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public Mono<CategoryDto> createCategory(CategoryDto categoryDto) {
        return categoryRepository.findFirstByOrderByIdentifierDesc()
                .map(lastCategory -> lastCategory.getIdentifier() + 1)
                .defaultIfEmpty(1)
                .flatMap(newIdentifier -> {
                    categoryDto.setIdentifier(newIdentifier);
                    categoryDto.setStatus(true);
                    return categoryRepository.save(categoryDto);
                });
    }

    @Override
    public Mono<CategoryDto> updateCategory(int identifier, CategoryDto categoryDto) {
        return categoryRepository.findByIdentifier(identifier)
                .flatMap(existingCategory -> {
                    categoryDto.setId(existingCategory.getId());
                    categoryDto.setIdentifier(identifier);
                    categoryDto.setStatus(existingCategory.isStatus());
                    return categoryRepository.save(categoryDto);
                });
    }

    @Override
    public Mono<CategoryDto> logicalDeleteCategory(int identifier) {
        return categoryRepository.findByIdentifier(identifier)
                .flatMap(category -> {
                    if (!category.isStatus()) {
                        return Mono.just(category);
                    }
                    category.setStatus(false);
                    return categoryRepository.save(category);
                });
    }

    @Override
    public Mono<CategoryDto> restoreCategory(int identifier) {
        return categoryRepository.findByIdentifier(identifier)
                .flatMap(category -> {
                    if (category.isStatus()) {
                        return Mono.just(category);
                    }
                    category.setStatus(true);
                    return categoryRepository.save(category);
                });
    }

    @Override
    public Flux<CategoryDto> getAllCategories() {
        return categoryRepository.findAllByOrderByIdentifierAsc();
    }

    @Override
    public Mono<CategoryDto> getCategoryByIdentifier(int identifier) {
        return categoryRepository.findByIdentifier(identifier);
    }

    @Override
    public Flux<CategoryDto> getCategoriesByRestaurantIdentifier(int restaurantIdentifier) {
        return categoryRepository.findByRestaurantIdentifier(restaurantIdentifier);
    }
}
