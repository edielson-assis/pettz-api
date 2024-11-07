package br.com.pettz.dtos.response;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Builder
@Getter
@Schema(name = "Get Category Response DTO", description = "DTO to get an existing category")
public class CategoryWithIdResponse extends RepresentationModel<CategoryWithIdResponse> {

    @Schema(description = "Category ID", example = "99ac1044-0c6e-4950-bfd4-76a2f3e074ae")
    private UUID idCategory;

    @Schema(description = "Category name", example = "Pet Accessories")
    private String name;
}