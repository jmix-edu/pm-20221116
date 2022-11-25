package com.company.pm.services;

import com.company.pm.entity.Project;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.validation.group.UiComponentChecks;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Service
@Validated
public class ProjectService {

    @Autowired
    private DataManager dataManager;

    @Validated(value = {Default.class, UiCrossFieldChecks.class, UiComponentChecks.class})
    public Project saveProject(@Valid @NotNull Project project) {
        return dataManager.unconstrained().save(project);
    }

}
