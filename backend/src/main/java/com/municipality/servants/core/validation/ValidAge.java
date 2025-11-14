package com.municipality.servants.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AgeValidator.class)
@Documented
public @interface ValidAge {

    String message() default "Idade deve estar entre {min} e {max} anos";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 18;

    int max() default 75;
}
