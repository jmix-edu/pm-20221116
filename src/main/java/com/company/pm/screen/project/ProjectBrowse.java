package com.company.pm.screen.project;

import com.company.pm.security.specific.JmixPmProjectArchiveContext;
import com.company.pm.services.EnvService;
import com.company.pm.services.ProjectService;
import io.jmix.core.AccessManager;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

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
    @Autowired
    private Button archive;
    @Autowired
    private AccessManager accessManager;
    @Autowired
    private GroupTable<Project> projectsTable;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CollectionLoader<Project> projectsDl;
    @Autowired
    private CollectionContainer<Project> projectsDc;

    @Subscribe
    public void onInit(InitEvent event) {
        JmixPmProjectArchiveContext context = new JmixPmProjectArchiveContext();
        accessManager.applyRegisteredConstraints(context);
        archive.setEnabled(context.isPermitted());
    }

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

    @Subscribe("archive")
    public void onArchiveClick(Button.ClickEvent event) {
        Project project = projectsTable.getSingleSelected();
        if (project != null) {
            Project updated = projectService.archive(project);
            projectsDc.replaceItem(dataContext.merge(updated));
            dataContext.commit();
        }
    }

    @Subscribe("startProjectBtn")
    public void onStartProjectBtnClick(Button.ClickEvent event) {
        Project project = projectsTable.getSingleSelected();
        if (project != null) {
            projectService.startAndSaveProject(project, LocalDateTime.now());
            projectsDl.load();
        }
    }

}