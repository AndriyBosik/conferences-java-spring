package com.conferences.validator;

import com.conferences.annotation.Password;
import com.conferences.annotation.PasswordMatches;
import com.conferences.model.UserRegistrationData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
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
                exception.printStackTrace();
            }
            if (passwords.size() == 2) {
                break;
            }
        }
        return passwords.size() == 2 && Objects.equals(passwords.get(0), passwords.get(1));
    }
}
