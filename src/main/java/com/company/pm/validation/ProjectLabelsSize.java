package com.company.pm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ProjectLabelsSizeValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ProjectLabelsSize {

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String message() default "Project label size ${validatedValue} is invalid. Must be between {min} and {max}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
