package br.com.pettz.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryUpdateResponse;

public interface CategoryService {
    
    CategoryResponse register(CategoryRequest categoryRequest);

    CategoryResponse findByName(String name);

    Page<CategoryResponse> findAll(Pageable pageable);

    Page<CategoryUpdateResponse> findAllWithId(Pageable pageable);

    CategoryResponse update(UUID idCategory, CategoryRequest categoryRequest);

    void delete(String name);
}