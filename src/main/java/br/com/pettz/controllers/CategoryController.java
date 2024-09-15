package br.com.pettz.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import br.com.pettz.controllers.hateoas.CategoryModel;
import br.com.pettz.controllers.swagger.CategoryControllerSwagger;
import br.com.pettz.dtos.request.CategoryRequest;
import br.com.pettz.dtos.response.CategoryResponse;
import br.com.pettz.dtos.response.CategoryUpdateResponse;
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
    public ResponseEntity<CategoryResponse> register(@Valid @RequestBody CategoryRequest categoryRequest) {
        var category = service.register(categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping(path = "/get/{name}")
    public ResponseEntity<CategoryModel> findByName(@PathVariable String name) {
        var category = service.findByName(name);
        var categoryModel = new CategoryModel(category);
        categoryModel.add(linkTo(methodOn(CategoryController.class).findAll(Pageable.unpaged())).withRel("Categories List"));
        return new ResponseEntity<>(categoryModel, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryModel>> findAll(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var categories = service.findAll(pageable);
        var categoryModels = categories.map(CategoryModel::new);
        categoryModels.map(category -> category.add(linkTo(methodOn(CategoryController.class).findByName(category.getCategory().name())).withSelfRel()));
        return new ResponseEntity<>(categoryModels, HttpStatus.OK);
    }

    @GetMapping(path = "/admin")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Page<CategoryUpdateResponse>> findAllWithId(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var categories = service.findAllWithId(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Transactional
    @PatchMapping(path = "/admin/update/{categoryId}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<CategoryResponse> update(@PathVariable UUID categoryId, @Valid @RequestBody CategoryRequest categoryRequest) {
        var category = service.update(categoryId, categoryRequest);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping(path = "/admin/delete/{name}")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<Void> delete(@PathVariable String name) {
        service.delete(name);
        return ResponseEntity.noContent().build();
    }
}