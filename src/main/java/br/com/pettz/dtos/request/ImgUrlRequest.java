package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register Image URL Request DTO", description = "DTO to register a new img Url")
public record ImgUrlRequest(
    
    @Schema(description = "Image URL", example = "https://example.com/images/products/bluetooth-pro-headphones.jpg")
    @NotBlank(message = "Image URL is required") 
    String imgUrl) {}