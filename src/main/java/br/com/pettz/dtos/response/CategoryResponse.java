package br.com.pettz.dtos.response;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
@Schema(name = "Register Category Response DTO", description = "DTO to register a new Category")
public class CategoryResponse extends RepresentationModel<CategoryResponse> {

    @Schema(description = "Category name", example = "Electronics")
    private String name;
}