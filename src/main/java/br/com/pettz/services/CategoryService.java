package br.com.pettz.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryResponse;

public interface CategoryService {
    
    CategoryResponse register(CategoryRequest categoryRequest);

    Page <CategoryResponse> findAll(Pageable pageable);

    CategoryResponse update(CategoryRequest categoryRequest);

    void delete(CategoryRequest categoryRequest);
}