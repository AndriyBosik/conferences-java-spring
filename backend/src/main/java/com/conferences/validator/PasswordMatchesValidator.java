package com.conferences.validator;

import com.conferences.annotation.Password;
import com.conferences.annotation.PasswordMatches;
import com.conferences.model.UserRegistrationData;
import lombok.extern.log4j.Log4j2;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *     Defines method to validate model to contain passwords which have to match
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Log4j2
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    /**
     * <p>Validates model's passwords</p>
     * @param object
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        log.info("Validating passwords to match");
        List<String> passwords = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field: fields) {
            Password password = field.getAnnotation(Password.class);
            if (password == null) {
                continue;
            }
            field.setAccessible(true);
            try {
                passwords.add((String) field.get(object));
            } catch (IllegalAccessException exception) {
                log.error("Unable to access password field", exception);
            }
            if (passwords.size() == 2) {
                break;
            }
        }
        return passwords.size() == 2 && passwords.get(0) != null && passwords.get(0).equals(passwords.get(1));
    }
}
