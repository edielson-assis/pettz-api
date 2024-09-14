package br.com.pettz.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Register Category Response DTO", description = "DTO to register a new Category")
public record CategoryResponse(

    @Schema(description = "Category name", example = "Electronics")
    String name
) {}