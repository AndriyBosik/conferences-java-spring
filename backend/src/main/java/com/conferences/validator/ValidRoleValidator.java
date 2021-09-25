package com.conferences.validator;

import com.conferences.annotation.ValidRole;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Log4j2
public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        log.info("Validating role");
        return "user".equalsIgnoreCase(role) || "speaker".equalsIgnoreCase(role);
    }
}
