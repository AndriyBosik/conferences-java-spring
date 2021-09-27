package com.conferences.validator;

import com.conferences.annotation.ValidRole;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 *     Defines method to validate user's role while signing up
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

    /**
     * <p>Validates user's role</p>
     * @param role
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        log.info("Validating role");
        return "user".equalsIgnoreCase(role) || "speaker".equalsIgnoreCase(role);
    }
}
