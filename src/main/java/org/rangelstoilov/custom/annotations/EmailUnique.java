package org.rangelstoilov.custom.annotations;

import org.rangelstoilov.custom.validators.EmailExistsConstraintValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy= EmailExistsConstraintValidator.class)
public @interface EmailUnique {
    String message() default "Email doesn't exists";
    Class[] groups() default {};
    Class[] payload() default {};
}