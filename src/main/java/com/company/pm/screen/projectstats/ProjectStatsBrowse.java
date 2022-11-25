package com.company.pm.screen.projectstats;

import com.company.pm.entity.Project;
import com.company.pm.services.StatsService;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.company.pm.dtos.ProjectStats;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@UiController("ProjectStats.browse")
@UiDescriptor("project-stats-browse.xml")
@LookupComponent("projectStatsesTable")
public class ProjectStatsBrowse extends StandardLookup<ProjectStats> {

    @Autowired
    private StatsService statsService;
    @Autowired
    private GroupTable<ProjectStats> projectStatsesTable;
    @Autowired
    private Notifications notifications;

    @Install(to = "projectStatsesDl", target = Target.DATA_LOADER)
    private List<ProjectStats> projectStatsesDlLoadDelegate(LoadContext<ProjectStats> loadContext) {
        return statsService.getProjectStats();
    }

    @Subscribe("showProjectEndDate")
    public void onShowProjectEndDateClick(Button.ClickEvent event) {
        Project projectById = statsService.getProjectById(projectStatsesTable.getSingleSelected().getId());
        notifications.create(Notifications.NotificationType.SYSTEM).withCaption(projectById.getEndDate().toString()).show();
    }
}