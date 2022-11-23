package com.company.pm.validation;

import io.jmix.core.validation.group.UiCrossFieldChecks;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.groups.Default;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {TwoDatesOrderValidator.class})
@Target({PARAMETER, TYPE})
@Retention(RUNTIME)
public @interface TwoDatesOrder {

    String firstDate();

    String lastDate();

    Class<?> dateClass();

    String message() default "The {firstDate} must be less than the {lastDate}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

}
