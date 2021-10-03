package com.conferences.validator;

import com.conferences.annotation.ValidRole;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

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

    private List<String> roles;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        roles = Arrays.asList(constraintAnnotation.roles());
    }

    /**
     * <p>Validates user's role</p>
     * @param role field to validate
     * @param constraintValidatorContext
     * @return true if role is valid, false otherwise
     */
    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        log.info("Validating role");
        return role != null && roles.contains(role);
    }
}
