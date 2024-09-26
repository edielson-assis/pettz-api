package br.com.pettz.dtos.response;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Builder
@Schema(name = "Get Product Response DTO", description = "DTO to get an existing product")
public class ProductResponse extends RepresentationModel<ProductResponse> {
    
    @Schema(description = "Product code", example = "PRD001")
    private String code;

    @Schema(description = "Product name", example = "Premium Dog Bed")
    private String name;

    @Schema(description = "Product description", example = "Comfortable and durable dog bed with washable cover.")
    private String description;

    @Schema(description = "Product price", example = "129.99")
    private BigDecimal price;

    @Schema(description = "List of img Urls")
    private Set<ImgUrlResponse> imgUrls;

    @Schema(description = "List of colors")
    private Set<ColorResponse> colors;
}