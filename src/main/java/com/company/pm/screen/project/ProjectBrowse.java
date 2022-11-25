package com.company.pm.screen.project;

import com.company.pm.services.EnvService;
import io.jmix.core.DataManager;
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
    @Autowired
    private DataManager dataManager;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        Integer newProjectsCount = dataManager.loadValue(
                        "select count(e) from Project e " +
                                "where :session_isManager = TRUE " +
                                "and e.endDate is null " +
                                "and e.manager.id = :current_user_id",
                        Integer.class)
                .one();

        if (newProjectsCount != 0) {
            notifications.create()
                    .withPosition(Notifications.Position.TOP_RIGHT)
                    .withCaption("New project")
                    .withDescription("Projects with UNFINISHED status: " + newProjectsCount)
                    .show();
        }
    }



    @Subscribe("envBtn")
    public void onEnvBtnClick(Button.ClickEvent event) {
        notifications.create(Notifications.NotificationType.SYSTEM)
                .withCaption(envService.getEnv()).show();

    }
}