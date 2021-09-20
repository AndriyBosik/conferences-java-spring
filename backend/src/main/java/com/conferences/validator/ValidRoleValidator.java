package com.conferences.validator;

import com.conferences.annotation.ValidRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        return "user".equalsIgnoreCase(role) || "speaker".equalsIgnoreCase(role);
    }
}
