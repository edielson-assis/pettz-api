package br.com.pettz.utils.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

import br.com.pettz.utils.validation.impl.CategoryValidatorImpl;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryValidatorImpl.class)
@Documented
public @interface CategoryValidator {
    String message() default "Invalid category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
