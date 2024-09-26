package br.com.pettz.dtos.request;

import java.math.BigDecimal;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "Register Product Request DTO", description = "DTO to register a new product")
public record ProductRequest(

    @Schema(description = "Product code", example = "PRD001")
    @NotBlank(message = "Code is required")
    String code,

    @Schema(description = "Product name", example = "Premium Dog Bed")
    @NotBlank(message = "Name is required")
    String name,

    @Schema(description = "Product description", example = "Comfortable and durable dog bed with washable cover.")
    @NotBlank(message = "Description is required")
    String description,

    @Schema(description = "Product price", example = "129.99")
    @Positive(message = "Value must be positive")
    @NotNull(message = "Price is required")
    BigDecimal price,

    @Schema(description = "List of categories")
    @NotEmpty(message = "A list of categories cannot be empty")
    Set<@Valid CategoryRequest> categories,

    @Schema(description = "List of img Urls")
    @NotEmpty(message = "A list of imgUrls cannot be empty")
    Set<@Valid ImgUrlRequest> imgUrls,

    @Schema(description = "List of colors")
    @NotEmpty(message = "A list of colors cannot be empty")
    Set<@Valid ColorRequest> colors
) {}