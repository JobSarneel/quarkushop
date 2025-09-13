package nl.spacedebris.monolith.service;

import lombok.extern.slf4j.Slf4j;
import nl.spacedebris.monolith.model.Category;
import nl.spacedebris.monolith.repository.CategoryRepository;
import nl.spacedebris.monolith.repository.ProductRepository;
import nl.spacedebris.monolith.repository.web.dto.CategoryDto;
import nl.spacedebris.monolith.repository.web.dto.ProductDto;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@Transactional
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;
    @Inject
    ProductRepository productRepository;

    public static CategoryDto mapToDto(Category category, Long productsCount) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productsCount
        );
    }

    public List<CategoryDto> findAll() {
        log.debug("Request to get all Categories");
        return this.categoryRepository.findAll()
                .stream()
                .map(category -> mapToDto(category, productRepository.countAllByCategoryId(category.getId())))
                .collect(Collectors.toList());
    }

    public CategoryDto findById(Long id) {
        log.debug("Request to get Category : {}", id);
        return this.categoryRepository.findById(id)
                .map(category -> mapToDto(category, productRepository.countAllByCategoryId(category.getId())))
                .orElse(null);
    }

    public CategoryDto create(CategoryDto categoryDto) {
        log.debug("Request to create Category : {}", categoryDto);
        return mapToDto(this.categoryRepository
                .save(new Category(categoryDto.getName(), categoryDto.getDescription())), 0L);
    }

    public void delete(Long id) {
        log.debug("Request to delete Category : {}", id);
        log.debug("Deleting all products for the Category : {}", id);
        this.productRepository.deleteAllByCategoryId(id);
        log.debug("Deleting Category : {}", id);
        this.categoryRepository.deleteById(id);
    }

    public List<ProductDto> findProductsByCategoryId(Long id) {
        return this.productRepository.findAllByCategoryId(id)
                .stream()
                .map(ProductService::mapToDto)
                .toList();
    }
}
