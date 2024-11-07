package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Get Color Response DTO", description = "DTO to get an existing color")
public record ColorWithIdResponse(
    
    @Schema(description = "Color ID", example = "99ac1044-0c6e-4950-bfd4-76a2f3e074ae")
    UUID idColor,

    @Schema(description = "Color name", example = "Light blue")
    String color) {}