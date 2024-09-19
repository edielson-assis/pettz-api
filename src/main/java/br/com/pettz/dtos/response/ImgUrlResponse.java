package br.com.pettz.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Get Image URL Response DTO", description = "DTO to get an existing image URL")
public record ImgUrlResponse(
    
    @Schema(description = "Image URL", example = "https://example.com/images/products/bluetooth-pro-headphones.jpg")
    String imgUrl) {}