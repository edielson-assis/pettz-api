package br.com.pettz.controllers.swagger;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Products", description = "Product's methods")
public interface ProductControllerSwagger {
    
    static final String SECURITY_SCHEME_KEY = "bearer-key";
    
    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)},  
      summary = "Create a product",
      description = "Create a product. The response, if successful, is a JSON with information about created product.",
      tags = {"Products"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful create product", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request.", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content)
    })
    ResponseEntity<ProductResponse> registerNewProduct(ProductRequest productRequest);
    
    @Operation(
      summary = "Find product by name",
      description = "Find product by name. The response, if successful, is a JSON with information about product.",
      tags = {"Products"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful get product", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))}),
        @ApiResponse(responseCode = "404", description = "Not found - Category not found", content = @Content)
    })
    ResponseEntity<ProductResponse> findProductByName(String name);

    @Operation(
      summary = "Find all products",
      description = "Find all products. The response, if successful, is a JSON with information about products list.",
      tags = {"Products"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful get all products", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))})
    })
    ResponseEntity<Page<ProductResponse>> findAllProducts(Pageable pageable);

    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)},
      summary = "Find all products",
      description = "Find all products. The response, if successful, is a JSON with information about products list.",
      tags = {"Products"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful get all products", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))}),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content)
    })
    ResponseEntity<Page<ProductWithIdResponse>> findAllProductsWithId(Pageable pageable);

    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)}, 
      summary = "Update a product",
      description = "Update a product. The response, if successful, is a JSON with information about updated product.",
      tags = {"Products"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful update product", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ProductResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request.", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found - Category not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Conflict - Category's name already exists", content = @Content)
    })
    ResponseEntity<ProductResponse> updateProductById(UUID productId, ProductUpdateRequest productRequest);

    @Operation(
      security = {@SecurityRequirement(name = SECURITY_SCHEME_KEY)}, 
      summary = "Delete a product",
      description = "Delete a product. The response, if successful, is a JSON with information about deleted product.",
      tags = {"Products"}
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successful delete product", content = @Content),
        @ApiResponse(responseCode = "403", description = "Forbidden - Authentication problem", content = @Content),
        @ApiResponse(responseCode = "404", description = "Not found - Product not found", content = @Content)
    })
    ResponseEntity<Void> deleteProduct(UUID productId);
}