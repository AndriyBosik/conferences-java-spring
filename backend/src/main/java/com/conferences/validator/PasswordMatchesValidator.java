package com.conferences.validator;

import com.conferences.annotation.PasswordMatches;
import com.conferences.model.UserRegistrationData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserRegistrationData> {

    @Override
    public boolean isValid(UserRegistrationData data, ConstraintValidatorContext constraintValidatorContext) {
        return data.getPassword().equals(data.getConfirmPassword());
    }
}
