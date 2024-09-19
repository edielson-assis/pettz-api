package br.com.pettz.dtos.response;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
@Schema(name = "Get Category with product Response DTO", description = "DTO to get a existing Category")
public class CategoryProductResponse extends RepresentationModel<CategoryProductResponse> {

    @Schema(description = "Category name", example = "Electronics")
    private String name;

    @Schema(description = "List of categories")
    private Set<ProductResponse> products;
}