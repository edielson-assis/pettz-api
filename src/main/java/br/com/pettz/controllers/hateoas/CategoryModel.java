package br.com.pettz.controllers.hateoas;

import org.springframework.hateoas.RepresentationModel;

import br.com.pettz.dtos.response.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
public class CategoryModel extends RepresentationModel<CategoryModel> {

    private final CategoryResponse category;
}