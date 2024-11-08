package br.com.pettz.dtos.request;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(name = "Update Product Request DTO", description = "DTO to update a existing product")
public record ProductUpdateRequest(

    @Schema(description = "Product name", example = "Premium Dog Bed")
    @NotBlank(message = "Name is required")
    String name,

    @Schema(description = "Product description", example = "Comfortable and durable dog bed with washable cover.")
    @NotBlank(message = "Description is required")
    String description,

    @Schema(description = "Product price", example = "129.99")
    @Positive(message = "Value must be positive")
    @NotNull(message = "Price is required")
    BigDecimal price
) {}