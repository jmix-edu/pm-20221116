package com.company.pm.screen.projectbudget;

import io.jmix.ui.screen.*;
import com.company.pm.entity.ProjectBudget;

@UiController("ProjectBudget.edit")
@UiDescriptor("project-budget-edit.xml")
@EditedEntityContainer("projectBudgetDc")
public class ProjectBudgetEdit extends StandardEditor<ProjectBudget> {
}