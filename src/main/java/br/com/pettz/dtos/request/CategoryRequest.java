package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(name = "Register Category Request DTO", description = "DTO to register a new category")
public record CategoryRequest(

    @Schema(description = "Category name", example = "Pet Accessories")
    @NotBlank(message = "Name is required") 
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Only letters must be typed")
    String name
) {}