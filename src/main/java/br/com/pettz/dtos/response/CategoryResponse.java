package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Register Category Response DTO", description = "DTO to register a new Category")
public record CategoryResponse(

    @Schema(description = "Category ID", example = "99ac1044-0c6e-4950-bfd4-76a2f3e074ae")
    UUID idCategory,

    @Schema(description = "Category name", example = "Electronics")
    String name
) {}