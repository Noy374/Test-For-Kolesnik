package org.svarian.testforkolesnik.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.svarian.testforkolesnik.annotation.UniqueDepartmentName;
import org.svarian.testforkolesnik.annotation.UniqueUsername;
import org.svarian.testforkolesnik.repository.DepartmentRepository;
import org.svarian.testforkolesnik.repository.UserRepository;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String name , ConstraintValidatorContext constraintValidatorContext) {
        return name != null && !userRepository.existsByUsername(name);
    }
}

