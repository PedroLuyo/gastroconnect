package app.infrastructure.controller;


import app.domain.model.letterplate.CategoryDto;
import app.domain.ports.input.CategoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryUseCase.createCategory(categoryDto);
    }

    @GetMapping("/obtain")
    public Flux<CategoryDto> getAllCategories() {
        return categoryUseCase.getAllCategories();
    }

    @GetMapping("/obtain/{identifier}")
    public Mono<CategoryDto> getCategoryByIdentifier(@PathVariable int identifier) {
        return categoryUseCase.getCategoryByIdentifier(identifier);
    }

    @GetMapping("/obtain/restaurant/{restaurantIdentifier}")
    public Flux<CategoryDto> getCategoriesByRestaurantIdentifier(@PathVariable int restaurantIdentifier) {
        return categoryUseCase.getCategoriesByRestaurantIdentifier(restaurantIdentifier);
    }

    @PutMapping("/edit/{identifier}")
    public Mono<CategoryDto> updateCategory(
            @PathVariable int identifier,
            @RequestBody CategoryDto categoryDto) {
        return categoryUseCase.updateCategory(identifier, categoryDto);
    }

    @PutMapping("/delete/{identifier}")
    public Mono<CategoryDto> logicalDeleteCategory(@PathVariable int identifier) {
        return categoryUseCase.logicalDeleteCategory(identifier);
    }

    @PutMapping("/restore/{identifier}")
    public Mono<CategoryDto> restoreCategory(@PathVariable int identifier) {
        return categoryUseCase.restoreCategory(identifier);
    }
}
