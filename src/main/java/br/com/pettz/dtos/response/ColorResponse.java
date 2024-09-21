package br.com.pettz.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Get Color Response DTO", description = "DTO to get an existing color")
public record ColorResponse(
    
    @Schema(description = "Color name", example = "Light blue")
    String color) {}