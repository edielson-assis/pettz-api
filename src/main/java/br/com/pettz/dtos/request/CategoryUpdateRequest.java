package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "Update Category Request DTO", description = "DTO to update a existing category")
public record CategoryUpdateRequest(

    @Schema(description = "Category name", example = "Electronics")
    @NotBlank(message = "Name is required") 
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Only letters must be typed")
    String name
) {}