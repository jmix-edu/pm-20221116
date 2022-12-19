package com.company.pm.bdd.uimodel;

import com.codeborne.selenide.Condition;
import io.jmix.masquerade.Wire;
import io.jmix.masquerade.base.Composite;
import io.jmix.masquerade.component.Button;
import io.jmix.masquerade.component.DateField;
import io.jmix.masquerade.component.EntityPicker;
import io.jmix.masquerade.component.TextField;

public class TaskEdit extends Composite<TaskEdit> {

    @Wire
    private TextField nameField;
    @Wire
    private EntityPicker assigneeField;
    @Wire
    private DateField startDateField;
    @Wire
    private TextField hoursPlannedField;
    @Wire
    private EntityPicker projectField;
    @Wire
    private Button commitAndCloseBtn;

    public TextField getNameField() {
        return nameField;
    }

    public EntityPicker getAssigneeField() {
        return assigneeField;
    }

    public DateField getStartDateField() {
        return startDateField;
    }

    public TextField getHoursPlannedField() {
        return hoursPlannedField;
    }

    public EntityPicker getProjectField() {
        return projectField;
    }

    public void commitAndClose() {
        commitAndCloseBtn.shouldBe(Condition.visible)
                .click();
    }
}
