package com.conferences.validator;

import com.conferences.annotation.ValidEmail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

public class ValidEmailValidatorTest {

    private static ValidEmailValidator validator;
    private static ConstraintValidatorContext context;

    @BeforeAll
    private static void beforeAll() {
        validator = new ValidEmailValidator();
        validator.initialize(Mockito.mock(ValidEmail.class));
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    public void shouldReturnTrueForValidEmail() {
        assertTrue(validator.isValid("email@example.com", context));
    }

    @Test
    public void shouldReturnFalseForNonExistingDomain() {
        assertFalse(validator.isValid("example@", context));
    }

    @Test
    public void shouldReturnFalseForEmailWithoutAtSign() {
        assertFalse(validator.isValid("email.example.com", context));
    }
}
