package br.com.pettz.dtos.response;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
@Schema(name = "Get Product Response DTO", description = "DTO to get an existing product")
public class ProductWithIdResponse extends RepresentationModel<ProductWithIdResponse> {

    @Schema(description = "Product ID", example = "99ac1044-0c6e-4950-bfd4-76a2f3e074ae")
    private UUID idProduct;
    
    @Schema(description = "Product code", example = "PRD001")
    private String code;

    @Schema(description = "Product name", example = "Bluetooth Pro Headphones")
    private String name;

    @Schema(description = "Product description", example = "Wireless headphones with noise cancellation and 20h battery life.")
    private String description;
    private BigDecimal price;

    @Schema(description = "List of img Urls")
    private Set<ImgUrlWithIdResponse> imgUrls;

    @Schema(description = "List of colors")
    private Set<ColorWithIdResponse> colors;
}