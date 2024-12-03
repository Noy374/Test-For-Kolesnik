package org.svarian.testforkolesnik.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.svarian.testforkolesnik.validator.UniqueDepartmentNameValidator;
import org.svarian.testforkolesnik.validator.UniqueUsernameValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default  "Username must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

