package com.company.pm.screen.project;

import com.company.pm.services.EnvService;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {

    @Autowired
    EnvService envService;
    @Autowired
    private Notifications notifications;

    @Subscribe("envBtn")
    public void onEnvBtnClick(Button.ClickEvent event) {
        notifications.create(Notifications.NotificationType.SYSTEM)
                .withCaption(envService.getEnv()).show();

    }
}