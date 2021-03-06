package com.conferences.validator;

import com.conferences.annotation.ValidRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.*;

public class ValidRoleValidatorTest {

    private static ValidRoleValidator validator;
    private static ConstraintValidatorContext context;
    private static ValidRole constraintContext;

    @BeforeAll
    private static void beforeAll() {
        validator = new ValidRoleValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
        constraintContext = Mockito.mock(ValidRole.class);
        Mockito.when(constraintContext.roles()).thenReturn(new String[]{"speaker", "user"});
    }

    @BeforeEach
    private void beforeEach() {
        validator.initialize(constraintContext);
    }

    @Test
    public void shouldReturnTrueForSpeakerRole() {
        assertTrue(validator.isValid("speaker", context));
    }

    @Test
    public void shouldReturnTrueForUserRole() {
        assertTrue(validator.isValid("user", context));
    }

    @Test
    public void shouldReturnFalseForModeratorRole() {
        assertFalse(validator.isValid("moderator", context));
    }

    @Test
    public void shouldReturnTrueForRoleIgnoreCase() {
        assertTrue(validator.isValid("uSeR", context));
    }
}
