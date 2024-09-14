package br.com.pettz.mappers;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryUpdateResponse;
import br.com.pettz.models.Category;

public class CategoryMapper {
    
    private CategoryMapper() {}

    public static Category toEntity(CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.name()).build();
    }

    public static CategoryResponse toDto(Category category) {
        return CategoryResponse.builder()
                .name(category.getName()).build();
    }

    public static CategoryUpdateResponse toUpdateDto(Category category) {
        return CategoryUpdateResponse.builder()
                .idCategory(category.getIdCategory())
                .name(category.getName()).build();
    }

    public static void toUpdateEntity(Category category, CategoryRequest categoryRequest) {
        category.setName(categoryRequest.name());
    }
}