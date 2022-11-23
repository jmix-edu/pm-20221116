package com.company.pm.screen.project;

import com.company.pm.entity.ProjectBudget;
import com.company.pm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

@UiController("Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataContext dataContext;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Project> event) {
        UserDetails user = currentAuthentication.getUser();
        event.getEntity().setManager((User) user);

        ProjectBudget projectBudget = dataContext.create(ProjectBudget.class);
        event.getEntity().setBudget(projectBudget);
    }



}