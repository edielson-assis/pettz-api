package br.com.pettz.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pettz.controllers.swagger.ProductControllerSwagger;
import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;
import br.com.pettz.services.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces = "application/json")
public class ProductController implements ProductControllerSwagger {
    
    private final ProductService service;

    @Transactional
    @PostMapping(path = "/admin/register")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<ProductResponse> registerNewProduct(@Valid @RequestBody ProductRequest productRequest) {
        var product = service.registerNewProduct(productRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/{name}")
    @Override
    public ResponseEntity<ProductResponse> findProductByName(@PathVariable String name) {
        var product = service.findProductByName(name);
        product.add(linkTo(methodOn(ProductController.class).findAllProducts(Pageable.unpaged())).withRel("Products List"));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<ProductResponse>> findAllProducts(@PageableDefault(size = 10, sort = {"name"}, direction = Direction.DESC) Pageable pageable) {
        var products = service.findAllProducts(pageable);
        products.map(product -> product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName())).withSelfRel()));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/admin/getAll")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<Page<ProductWithIdResponse>> findAllProductsWithId(@PageableDefault(size = 10, sort = {"name"}, direction = Direction.DESC) Pageable pageable) {
        var products = service.findAllProductsWithId(pageable);
        products.map(product -> product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName())).withSelfRel()));
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Transactional
    @PutMapping(path = "/admin/update/{productId}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable UUID productId, @Valid @RequestBody ProductUpdateRequest productRequest) {
        var product = service.updateProductById(productId, productRequest);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/admin/delete/{productId}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}