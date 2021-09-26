package com.conferences.validator;

import com.conferences.model.UserRegistrationData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordMatchesValidatorTest {

    private static PasswordMatchesValidator validator;
    private static ConstraintValidatorContext context;

    @BeforeAll
    private static void beforeAll() {
        validator = new PasswordMatchesValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    public void shouldReturnFalseForTwoNullValues() {
        UserRegistrationData data = new UserRegistrationData();
        data.setPassword(null);
        data.setConfirmPassword(null);

        assertFalse(validator.isValid(data, context));
    }

    @Test
    public void shouldReturnTrueForMatchedPasswords() {
        UserRegistrationData data = new UserRegistrationData();
        data.setPassword("password");
        data.setConfirmPassword("password");

        assertTrue(validator.isValid(data, context));
    }

    @Test
    public void shouldReturnFalseForNotMatchedPasswords() {
        UserRegistrationData data = new UserRegistrationData();
        data.setPassword("password");
        data.setConfirmPassword(" password ");

        assertFalse(validator.isValid(data, context));
    }
}
