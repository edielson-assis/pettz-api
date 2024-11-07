package br.com.pettz.utils.validation.impl;

import java.util.Set;

import br.com.pettz.utils.validation.CategoryValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryValidatorImpl implements ConstraintValidator<CategoryValidator, String> {

    private static final String VALID_CATEGORY = "^[a-zA-ZÀ-ÿ\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && value.matches(VALID_CATEGORY);
    }

    public boolean isValid(Set<String> values, ConstraintValidatorContext context) {
        for (String value : values) {
            if (value == null || !value.matches(VALID_CATEGORY)) {
                return false;
            }
        }
        return true;
    }
}