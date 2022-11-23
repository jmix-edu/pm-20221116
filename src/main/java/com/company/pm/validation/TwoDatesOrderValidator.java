package com.company.pm.validation;

import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

public class TwoDatesOrderValidator implements ConstraintValidator<TwoDatesOrder, Object> {

    private String firstDate;
    private String lastDate;
    private Class<?> aClass;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Field firstDateFiled = ReflectionUtils.findField(value.getClass(), firstDate);
        firstDateFiled.setAccessible(true);
        Field lastDateFiled = ReflectionUtils.findField(value.getClass(), lastDate);
        lastDateFiled.setAccessible(true);
        try {
            LocalDateTime firstDateValue = (LocalDateTime)firstDateFiled.get(value);
            LocalDateTime lastDateValue = (LocalDateTime)lastDateFiled.get(value);
            return lastDateValue == null || firstDateValue.isBefore(lastDateValue);
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    @Override
    public void initialize(TwoDatesOrder constraintAnnotation) {
        firstDate = constraintAnnotation.firstDate();
        lastDate = constraintAnnotation.lastDate();
        aClass = constraintAnnotation.dateClass();
    }
}
