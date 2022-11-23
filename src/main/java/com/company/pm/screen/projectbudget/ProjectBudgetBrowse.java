package com.company.pm.screen.projectbudget;

import io.jmix.ui.screen.*;
import com.company.pm.entity.ProjectBudget;

@UiController("ProjectBudget.browse")
@UiDescriptor("project-budget-browse.xml")
@LookupComponent("projectBudgetsTable")
public class ProjectBudgetBrowse extends StandardLookup<ProjectBudget> {
}