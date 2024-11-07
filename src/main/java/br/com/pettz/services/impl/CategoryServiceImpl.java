package br.com.pettz.services.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryProductResponse;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryWithIdResponse;
import br.com.pettz.mappers.CategoryMapper;
import br.com.pettz.models.Category;
import br.com.pettz.models.Product;
import br.com.pettz.repositories.CategoryRepository;
import br.com.pettz.services.CategoryService;
import br.com.pettz.services.exceptions.DataBaseException;
import br.com.pettz.services.exceptions.ObjectNotFoundException;
import br.com.pettz.services.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private static final String CATEGORY_NOT_FOUND = "Category not found";
    private static final String CATEGORY_ALREADY_EXISTS = "Category already exists";

    @Override
    public CategoryResponse registerNewCategory(CategoryRequest categoryRequest) {
        Category category = CategoryMapper.toEntity(categoryRequest);
        validateCategoryExists(category);
        log.info("Registering a new Category: {}", category);
        return CategoryMapper.toDto(repository.save(category));
    }

    @Override
    public CategoryProductResponse findCategoryByNameWithProducts(String category) {
        log.info("Searching for category with name: {}", category);
        return repository.findByNameIgnoreCase(category).map(CategoryMapper::toCategoryProductDto).orElseThrow(() -> {
            log.error(CATEGORY_NOT_FOUND.concat(": {}"), category);
            return new ObjectNotFoundException(CATEGORY_NOT_FOUND);
        });
    }

    @Override
    public Page<CategoryResponse> findAllCategories(Pageable pageable) {
        log.info("Searching all Categories");
        return repository.findAll(pageable).map(CategoryMapper::toDto);
    }

    @Override
    public Page<CategoryWithIdResponse> findAllCategoriesWithId(Pageable pageable) {
        log.info("Searching all Categories");
        return repository.findAll(pageable).map(CategoryMapper::toCategoryWithIdDto);
    }

    @Override
    public CategoryResponse updateCategoryById(UUID idCategory, CategoryRequest categoryRequest) {
        Category category = findByCategory(idCategory);
        CategoryMapper.toUpdateEntity(category, categoryRequest);
        validateCategoryNotExists(category);
        log.info("Updating Category with name: {}", category.getName());
        return CategoryMapper.toDto(repository.save(category));
    }

    @Override
    public void deleteCategory(String categoryRequest) {
        Category category = findByCategory(categoryRequest);
        try {
            log.info("Attempting to delete category with name: {}", category.getName());
            repository.delete(category);
            log.info("Category with name: {} successfully deleted", category.getName());
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to delete category with name: {} due to referential integrity violation", category.getName(), e);
            throw new DataBaseException(e.getMessage());
        }
    }

    @Override
    public synchronized Set<Category> findByCategories(Set<String> categories, Product product) {
        Set<Category> processedCategories = new HashSet<>();

        for (String categoryName : categories) {
            log.info("Searching for category: {}", categoryName);
            Category existingCategory = repository.findByNameIgnoreCase(categoryName).orElseGet(() -> {
                log.info("Registering a new category: {}", categoryName);
                return repository.save(new Category(UUID.randomUUID(), categoryName));
            });
            processedCategories.add(existingCategory);
        }

        for (Category category : processedCategories) {
            category.getProducts().add(product);
        }
        return processedCategories;
    }

    private synchronized void validateCategoryExists(Category category) {
        boolean exists = repository.existsByNameIgnoreCase(category.getName());
        if (exists) {
            log.error(CATEGORY_ALREADY_EXISTS.concat(": {}"), category.getName());
            throw new ValidationException(CATEGORY_ALREADY_EXISTS);
        }
    }

    private synchronized void validateCategoryNotExists(Category category) {
        boolean exists = repository.existsByNameAndIdCategoryNot(category.getName(), category.getIdCategory());
        if (exists) {
            log.error(CATEGORY_ALREADY_EXISTS.concat(": {}"), category.getName());
            throw new ValidationException(CATEGORY_ALREADY_EXISTS);
        }
    }

    private Category findByCategory(String category) {
        log.info("Searching for category with name: {}", category);
        return repository.findByNameIgnoreCase(category).orElseThrow(() -> {
            log.error(CATEGORY_NOT_FOUND.concat(": {}"), category);
            return new ObjectNotFoundException(CATEGORY_NOT_FOUND);
        });
    }

    private Category findByCategory(UUID categoryId) {
        log.info("Searching for category with ID: {}", categoryId);
        return repository.findById(categoryId).orElseThrow(() -> {
            log.error(CATEGORY_NOT_FOUND.concat(": {}"), categoryId);
            return new ObjectNotFoundException(CATEGORY_NOT_FOUND);
        });
    }
}