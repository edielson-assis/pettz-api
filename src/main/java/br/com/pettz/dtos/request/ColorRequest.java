package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register Color Request DTO", description = "DTO to register a new color")
public record ColorRequest(
    
    @Schema(description = "Color name", example = "Light blue")
    @NotBlank(message = "Name is required") 
    String name) {}