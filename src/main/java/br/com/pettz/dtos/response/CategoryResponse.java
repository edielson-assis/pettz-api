package br.com.pettz.dtos.response;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
@Schema(name = "Get Category Response DTO", description = "DTO to get an existing Category")
public class CategoryResponse extends RepresentationModel<CategoryResponse> {

    @Schema(description = "Category name", example = "Pet Accessories")
    private String name;
}