package org.svarian.testforkolesnik.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.svarian.testforkolesnik.validator.UniqueDepartmentNameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueDepartmentNameValidator.class)
public @interface UniqueDepartmentName {
    String message() default "Department name must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

