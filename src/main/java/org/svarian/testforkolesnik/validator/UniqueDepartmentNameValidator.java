package org.svarian.testforkolesnik.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.svarian.testforkolesnik.annotation.UniqueDepartmentName;
import org.svarian.testforkolesnik.repository.DepartmentRepository;

@RequiredArgsConstructor
public class UniqueDepartmentNameValidator implements ConstraintValidator<UniqueDepartmentName, String> {

    private final DepartmentRepository departmentRepository;

    @Override
    public boolean isValid(String name , ConstraintValidatorContext constraintValidatorContext) {
        return name != null && !departmentRepository.existsByName(name);
    }
}

