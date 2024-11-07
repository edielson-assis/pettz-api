package br.com.pettz.controllers.swagger;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryProductResponse;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryWithIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Categories", description = "Category's methods")
public interface CategoryControllerSwagger {

    static final String SECURITY_SCHEME_KEY = "bearer-key";
    
    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)},  
      summary = "Create a category",
      description = "Create a category. The response, if successful, is a JSON with information about created category.",
      tags = {"Categories"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful create category", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request.", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content)
    })
    ResponseEntity<CategoryResponse> registerNewCategory(CategoryRequest categoryRequest);

    @Operation(
      summary = "Find category by name",
      description = "Find category by name. The response, if successful, is a JSON with information about category.",
      tags = {"Categories"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful get category", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Not found - Category not found", content = @Content)
    })
    ResponseEntity<CategoryProductResponse> findCategoryByNameWithProducts(String name);

    @Operation(
      summary = "Find all categories",
      description = "Find all categories. The response, if successful, is a JSON with information about categories list.",
      tags = {"Categories"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful get all categories", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))})
    })
    ResponseEntity<Page<CategoryResponse>> findAllCategories(Pageable pageable);

    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)},
      summary = "Find all categories",
      description = "Find all categories. The response, if successful, is a JSON with information about categories list.",
      tags = {"Categories"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful get all categories", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))}),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content)
    })
    ResponseEntity<Page<CategoryWithIdResponse>> findAllCategoriesWithId(Pageable pageable);

    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)}, 
      summary = "Update a category",
      description = "Update a category. The response, if successful, is a JSON with information about updated category.",
      tags = {"Categories"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful update category", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = CategoryResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request.", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found - Category not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict - Category's name already exists", content = @Content)
    })
    ResponseEntity<CategoryResponse> updateCategoryById(UUID categoryId, CategoryRequest categoryRequest);

    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)}, 
      summary = "Delete a category",
      description = "Delete a category. The response, if successful, is a JSON with information about deleted category.",
      tags = {"Categories"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful delete category", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found - Category not found", content = @Content)
    })
    ResponseEntity<Void> deleteCategory(String name);
}