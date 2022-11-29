package com.company.pm.services;

import com.company.pm.entity.Project;
import io.jmix.core.DataManager;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.time.LocalDateTime;

@Service
@Validated
public class ProjectService {

    @Autowired
    private DataManager dataManager;

    @Validated(value = {Default.class, UiCrossFieldChecks.class, UiComponentChecks.class})
    public Project saveProject(@Valid @NotNull Project project) {
        return dataManager.unconstrained().save(project);
    }

    @Validated(value = {Default.class, UiCrossFieldChecks.class, UiComponentChecks.class})
    public Project startAndSaveProject(@Valid @NotNull Project project, @NotNull LocalDateTime startDate) {
        project.setStartDate(startDate);
        return dataManager.unconstrained().save(project);
    }

    @Validated(value = {Default.class, UiCrossFieldChecks.class, UiComponentChecks.class})
    public Project archive(@Valid @NotNull Project project) {
        project.setArchived(true);
        return project;
    }

}
