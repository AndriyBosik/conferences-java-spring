package com.conferences.annotation;

import com.conferences.validator.ValidRoleValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * <p>
 *     Annotation used to validate model's role
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/27
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidRoleValidator.class)
@Documented
public @interface ValidRole {
    String message() default "Invalid role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
