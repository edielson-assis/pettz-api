package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Register User Response DTO", description = "DTO to log in and register a new user")
public record CategoryResponse(

    UUID idCategory,

    @Schema(description = "Category name", example = "Electronics")
    String name
) {}