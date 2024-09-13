package br.com.pettz.services.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.mappers.CategoryMapper;
import br.com.pettz.models.Category;
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

    @Override
    public CategoryResponse register(CategoryRequest categoryRequest) {
        Category category = CategoryMapper.toEntity(categoryRequest);
        validateCategoryNotExists(category);
        log.info("Registering a new Category");
        return CategoryMapper.toDto(repository.save(category));
    }

    public Category findByName(CategoryRequest categoryRequest) {
        log.info("Searching for category with name: {}", categoryRequest.name());
        return repository.findByNameIgnoreCase(categoryRequest.name()).orElseThrow(() -> {
            log.error("Category not found: {}", categoryRequest.name());
            return new ObjectNotFoundException("Category not found");
        });
    }

    @Override
    public Page<CategoryResponse> findAll(Pageable pageable) {
        log.info("Searching all Categories");
        return repository.findAll(pageable).map(CategoryMapper::toDto);
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest) {
        Category category = findByName(categoryRequest);
        CategoryMapper.toUpdateEntity(category, categoryRequest);
        validateCategoryNotExists(category);
        log.info("Updating Category with name: {}", category.getName());
        return CategoryMapper.toDto(repository.save(category));
    }

    @Override
    public void delete(CategoryRequest categoryRequest) {
        Category category = findByName(categoryRequest);
        try {
            log.info("Attempting to delete category with name: {}", category.getName());
            repository.delete(category);
            log.info("Category with name: {} successfully deleted", category.getName());
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to delete category with name: {} due to referential integrity violation", category.getName(), e);
            throw new DataBaseException(e.getMessage());
        }
    }
    
    private synchronized void validateCategoryNotExists(Category category) {
        boolean existeEmail = repository.existsByName(category.getName());
        if (existeEmail) {
            log.error("Category already exists: {}", category.getName());
            throw new ValidationException("Category already exists");
        }
    }
}