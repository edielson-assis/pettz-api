package br.com.pettz.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryProductResponse;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryWithIdResponse;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.models.Category;
import br.com.pettz.models.Product;

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

    public static CategoryProductResponse toCategoryProductDto(Category category) {
        return CategoryProductResponse.builder()
                .name(category.getName())
                .products(products(category.getProducts())).build();
    }

    public static CategoryWithIdResponse toCategoryWithIdDto(Category category) {
        return CategoryWithIdResponse.builder()
                .idCategory(category.getIdCategory())
                .name(category.getName()).build();
    }

    public static void toUpdateEntity(Category category, CategoryRequest categoryRequest) {
        category.setName(categoryRequest.name());
    }

    private static Set<ProductResponse> products(Set<Product> categories) {
        return categories.stream().map(ProductMapper::toDto).collect(Collectors.toSet());
    }
}