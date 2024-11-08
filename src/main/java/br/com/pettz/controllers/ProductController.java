package br.com.pettz.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pettz.controllers.swagger.ProductControllerSwagger;
import br.com.pettz.dtos.request.ProductRequest;
import br.com.pettz.dtos.request.ProductUpdateRequest;
import br.com.pettz.dtos.response.ProductResponse;
import br.com.pettz.dtos.response.ProductWithIdResponse;
import br.com.pettz.services.ProductService;
import br.com.pettz.utils.constants.DefaultValue;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/products", produces = "application/json")
public class ProductController implements ProductControllerSwagger {
    
    private final ProductService service;
    private final PagedResourcesAssembler<ProductResponse> assembler;
    private final PagedResourcesAssembler<ProductWithIdResponse> assemblerId;

    @Transactional
    @PostMapping(path = "/admin", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<ProductResponse> registerNewProduct(@Valid ProductRequest productRequest) {
        var product = service.registerNewProduct(productRequest);
        product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName(), DefaultValue.PAGE, DefaultValue.SIZE, DefaultValue.DIRECTION)).withSelfRel());
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{name}")
    @Override
    public ResponseEntity<PagedModel<EntityModel<ProductResponse>>> findProductByName(
            @PathVariable(value = "name") String name, 
            @RequestParam(value = "page", defaultValue = "0") Integer page, 
            @RequestParam(value = "size", defaultValue = "10") Integer size, 
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var products = service.findProductByName(name, page, size, direction);
        var link = linkTo(methodOn(ProductController.class).findProductByName(name, page, size, direction)).withSelfRel();
        products.map(product -> product.add(linkTo(methodOn(ProductController.class).findProductByName(name, page, size, direction)).withSelfRel()));
        return new ResponseEntity<>(assembler.toModel(products, link), HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<PagedModel<EntityModel<ProductResponse>>> findAllProducts(
            @RequestParam(value = "page", defaultValue = "0") Integer page, 
            @RequestParam(value = "size", defaultValue = "10") Integer size, 
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var products = service.findAllProducts(page, size, direction);
        var link = linkTo(methodOn(ProductController.class).findAllProducts(page, size, direction)).withSelfRel();
        products.map(product -> product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName(), DefaultValue.PAGE, DefaultValue.SIZE, DefaultValue.DIRECTION)).withSelfRel()));
        return new ResponseEntity<>(assembler.toModel(products, link), HttpStatus.OK);
    }

    @GetMapping(path = "/admin")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<PagedModel<EntityModel<ProductWithIdResponse>>> findAllProductsWithId(
            @RequestParam(value = "page", defaultValue = "0") Integer page, 
            @RequestParam(value = "size", defaultValue = "10") Integer size, 
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var products = service.findAllProductsWithId(page, size, direction);
        var link = linkTo(methodOn(ProductController.class).findAllProductsWithId(page, size, direction)).withSelfRel();
        products.map(product -> product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName(), DefaultValue.PAGE, DefaultValue.SIZE, DefaultValue.DIRECTION)).withSelfRel()));
        return new ResponseEntity<>(assemblerId.toModel(products, link), HttpStatus.OK);
    }

    @GetMapping("/images/{filename}")
    @Override
    public ResponseEntity<Resource> getImages(@PathVariable String filename) {
        Resource file = service.getImages(filename);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(file);
    }

    @Transactional
    @PutMapping(path = "/admin/{productId}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable UUID productId, @Valid @RequestBody ProductUpdateRequest productRequest) {
        var product = service.updateProductById(productId, productRequest);
        product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName(), DefaultValue.PAGE, DefaultValue.SIZE, DefaultValue.DIRECTION)).withSelfRel());
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/admin/{productId}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        service.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}