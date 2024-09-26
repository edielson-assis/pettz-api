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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pettz.controllers.swagger.CategoryControllerSwagger;
import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryProductResponse;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryWithIdResponse;
import br.com.pettz.services.CategoryService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/categories", produces = "application/json")
public class CategoryController implements CategoryControllerSwagger {
    
    private final CategoryService service;

    @Transactional
    @PostMapping(path = "/admin/register")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<CategoryResponse> registerNewCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        var category = service.registerNewCategory(categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/{name}")
    @Override
    public ResponseEntity<CategoryProductResponse> findCategoryByNameWithProducts(@PathVariable String name) {
        var category = service.findCategoryByNameWithProducts(name);
        category.add(linkTo(methodOn(CategoryController.class).findAllCategories(Pageable.unpaged())).withRel("Categories List"));
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<CategoryResponse>> findAllCategories(@PageableDefault(size = 10, sort = {"name"}, direction = Direction.DESC) Pageable pageable) {
        var categories = service.findAllCategories(pageable);
        categories.map(category -> category.add(linkTo(methodOn(CategoryController.class).findCategoryByNameWithProducts(category.getName())).withSelfRel()));
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(path = "/admin/getAll")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<Page<CategoryWithIdResponse>> findAllCategoriesWithId(@PageableDefault(size = 10, sort = {"name"}, direction = Direction.DESC) Pageable pageable) {
        var categories = service.findAllCategoriesWithId(pageable);
        categories.map(category -> category.add(linkTo(methodOn(CategoryController.class).findCategoryByNameWithProducts(category.getName())).withSelfRel()));
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping(path = "/admin/update/{categoryId}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<CategoryResponse> updateCategoryById(@PathVariable UUID categoryId, @Valid @RequestBody CategoryRequest categoryRequest) {
        var category = service.updateCategoryById(categoryId, categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/admin/delete/{name}")
    @PreAuthorize("hasAuthority('Admin')")
    @Override
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        service.deleteCategory(name);
        return ResponseEntity.noContent().build();
    }
}