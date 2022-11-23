package com.company.pm.validation;

import com.company.pm.entity.ProjectLabels;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProjectLabelsSizeValidator implements ConstraintValidator<ProjectLabelsSize, ProjectLabels> {

    private int min;
    private int max;

    @Override
    public boolean isValid(ProjectLabels value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int size = value.getLabels().size();
        return size >= min && size <= max;
    }

    @Override
    public void initialize(ProjectLabelsSize constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }
}
