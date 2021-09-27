package com.conferences.validator;

import com.conferences.annotation.ValidEmail;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * <p>
 *     Defines method to validate email
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";

    private Pattern pattern;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    /**
     * <p>Validates email</p>
     * @param email email to be validated
     * @param constraintValidatorContext provides contextual data and operation when applying a given constraint validator
     * @return true if email is valid, false otherwise
     */
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        log.info("validating email");
        return pattern.matcher(email).matches();
    }
}
