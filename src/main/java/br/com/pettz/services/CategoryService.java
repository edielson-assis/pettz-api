package br.com.pettz.services;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryProductResponse;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryWithIdResponse;
import br.com.pettz.models.Category;
import br.com.pettz.models.Product;

public interface CategoryService {
    
    CategoryResponse registerNewCategory(CategoryRequest categoryRequest);

    CategoryProductResponse findCategoryByNameWithProducts(String name);

    Page<CategoryResponse> findAllCategories(Pageable pageable);

    Page<CategoryWithIdResponse> findAllCategoriesWithId(Pageable pageable);

    Set<Category> findByCategories(Set<String> categories, Product product);

    CategoryResponse updateCategoryById(UUID idCategory, CategoryRequest categoryRequest);

    void deleteCategory(String name);
}