package com.company.pm.screen.project;

import com.company.pm.entity.ProjectBudget;
import com.company.pm.entity.User;
import com.company.pm.services.ProjectService;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.ValidationErrors;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Collection;
import java.util.Set;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Validator validator;
    @Autowired
    private ScreenValidation screenValidation;
    @Autowired
    private Form form;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (getEditedEntity().getBudget() == null) {
            getEditedEntity().setBudget(dataContext.create(ProjectBudget.class));
        }
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<Project> event) {
        UserDetails user = currentAuthentication.getUser();
        event.getEntity().setManager((User) user);

        ProjectBudget projectBudget = dataContext.create(ProjectBudget.class);
        event.getEntity().setBudget(projectBudget);
    }

    @Subscribe("saveUsingService")
    public void onSaveUsingServiceClick(Button.ClickEvent event) {
        try {
            projectService.saveProject(getEditedEntity());
            closeWithDiscard();
        } catch (ConstraintViolationException e) {
            StringBuilder sb = new StringBuilder();

            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
                sb.append(constraintViolation.getMessage()).append("\n");
            }

            notifications.create(Notifications.NotificationType.TRAY)
                    .withCaption(sb.toString())
                    .show();        }
    }

    @Subscribe("validateDate")
    public void onValidateDateClick(Button.ClickEvent event) {
        ValidationErrors componentsErrors = screenValidation.validateUiComponents(form);
        ValidationErrors entityErrors = screenValidation.validateCrossFieldRules(this, getEditedEntity());
        entityErrors.addAll(componentsErrors);
        screenValidation.showValidationErrors(this, entityErrors);
    }



}