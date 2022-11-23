package com.company.pm.screen.projectstats;

import com.company.pm.services.StatsService;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
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

    @Install(to = "projectStatsesDl", target = Target.DATA_LOADER)
    private List<ProjectStats> projectStatsesDlLoadDelegate(LoadContext<ProjectStats> loadContext) {
        return statsService.getProjectStats();
    }
}