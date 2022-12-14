package com.company.pm.screen.projectstats;

import com.company.pm.entity.Project;
import com.company.pm.services.ReportingService;
import com.company.pm.services.StatsService;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reportsui.runner.ParametersDialogShowMode;
import io.jmix.reportsui.runner.UiReportRunner;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.TabSheet;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import com.company.pm.dtos.ProjectStats;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

@Route(value = "stats", root = true)
@UiController("ProjectStats.browse")
@UiDescriptor("project-stats-browse.xml")
@LookupComponent("projectStatsesTable")
public class ProjectStatsBrowse extends StandardLookup<ProjectStats> {

    @Autowired
    private StatsService statsService;
    private GroupTable<ProjectStats> projectStatsesTable;
    @Autowired
    private Notifications notifications;
    @Autowired
    private UiReportRunner uiReportRunner;
    @Autowired
    private ReportingService reportingService;
    @Autowired
    private CollectionLoader<Project> projectsDl;
    @Autowired
    private CollectionLoader<ProjectStats> projectStatsesDl;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        projectsDl.load();
    }


    @Install(to = "projectStatsesDl", target = Target.DATA_LOADER)
    private List<ProjectStats> projectStatsesDlLoadDelegate(LoadContext<ProjectStats> loadContext) {
        return statsService.getProjectStats();
    }

    @Subscribe("showProjectEndDate")
    public void onShowProjectEndDateClick(Button.ClickEvent event) {
        Project projectById = statsService.getProjectById(projectStatsesTable.getSingleSelected().getId());
        notifications.create(Notifications.NotificationType.SYSTEM).withCaption(projectById.getEndDate().toString()).show();
    }

    @Subscribe("budgetReportBtn")
    public void onBudgetReportBtnClick(Button.ClickEvent event) {
        uiReportRunner.byReportCode("project-budgets")
                .withOutputType(ReportOutputType.XLSX)
                .withParametersDialogShowMode(ParametersDialogShowMode.NO)
                .withOutputNamePattern("Project Budgets From Screen.xlsx")
                .runAndShow();
    }

    @Subscribe("reportInBgd")
    public void onReportInBgdClick(Button.ClickEvent event) {
        reportingService.runReport();
    }

    @Subscribe("projectsTabSheet")
    public void onProjectsTabSheetSelectedTabChange(TabSheet.SelectedTabChangeEvent event) {
        if (projectStatsesTable != null) return;
        projectStatsesDl.load();
        projectStatsesTable = (GroupTable<ProjectStats>) getWindow().getComponentNN("projectStatsesTable");
    }
}