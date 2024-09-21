package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Get Image URL Response DTO", description = "DTO to get an existing image URL")
public record ImgUrlWithIdResponse(
    
    @Schema(description = "Image URL ID", example = "99ac1044-0c6e-4950-bfd4-76a2f3e074ae")
    UUID idImgUrl,

    @Schema(description = "Image URL", example = "https://example.com/images/products/bluetooth-pro-headphones.jpg")
    String imgUrl) {}