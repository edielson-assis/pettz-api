package br.com.pettz.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pettz.controllers.swagger.CategoryControllerSwagger;
import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryProductResponse;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryWithIdResponse;
import br.com.pettz.services.CategoryService;
import br.com.pettz.utils.constants.DefaultValue;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/categories", produces = "application/json")
public class CategoryController implements CategoryControllerSwagger {
    
    private final CategoryService service;
    private final PagedResourcesAssembler<CategoryResponse> assembler;
    private final PagedResourcesAssembler<CategoryWithIdResponse> assemblerId;

    @Transactional
    @PostMapping(path = "/admin")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<CategoryResponse> registerNewCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        var category = service.registerNewCategory(categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{name}")
    @Override
    public ResponseEntity<CategoryProductResponse> findCategoryByNameWithProducts(@PathVariable String name) {
        var category = service.findCategoryByNameWithProducts(name);
        category.getProducts().forEach(product -> product.add(linkTo(methodOn(ProductController.class).findProductByName(product.getName(), DefaultValue.PAGE, DefaultValue.SIZE, DefaultValue.DIRECTION)).withSelfRel()));
        category.add(linkTo(methodOn(CategoryController.class).findAllCategories(DefaultValue.PAGE, DefaultValue.SIZE, DefaultValue.DIRECTION)).withRel("Categories List"));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<PagedModel<EntityModel<CategoryResponse>>> findAllCategories(
            @RequestParam(value = "page", defaultValue = "0") Integer page, 
            @RequestParam(value = "size", defaultValue = "10") Integer size, 
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var categories = service.findAllCategories(page, size, direction);
        var link = linkTo(methodOn(CategoryController.class).findAllCategoriesWithId(page, size, direction)).withSelfRel();
        categories.map(category -> category.add(linkTo(methodOn(CategoryController.class).findCategoryByNameWithProducts(category.getName())).withSelfRel()));
        return new ResponseEntity<>(assembler.toModel(categories, link), HttpStatus.OK);
    }

    @GetMapping(path = "/admin")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<PagedModel<EntityModel<CategoryWithIdResponse>>> findAllCategoriesWithId(
            @RequestParam(value = "page", defaultValue = "0") Integer page, 
            @RequestParam(value = "size", defaultValue = "10") Integer size, 
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {

        var categories = service.findAllCategoriesWithId(page, size, direction);
        var link = linkTo(methodOn(CategoryController.class).findAllCategoriesWithId(page, size, direction)).withSelfRel();
        categories.map(category -> category.add(linkTo(methodOn(CategoryController.class).findCategoryByNameWithProducts(category.getName())).withSelfRel()));
        return new ResponseEntity<>(assemblerId.toModel(categories, link), HttpStatus.OK);
    }

    @Transactional
    @PatchMapping(path = "/admin/{categoryId}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<CategoryResponse> updateCategoryById(@PathVariable UUID categoryId, @Valid @RequestBody CategoryRequest categoryRequest) {
        var category = service.updateCategoryById(categoryId, categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/admin/{name}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        service.deleteCategory(name);
        return ResponseEntity.noContent().build();
    }
}