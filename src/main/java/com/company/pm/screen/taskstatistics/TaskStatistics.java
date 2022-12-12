package com.company.pm.screen.taskstatistics;

import com.company.pm.entity.User;
import com.company.pm.services.StatsService;
import io.jmix.core.DataManager;
import io.jmix.core.FluentValuesLoader;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.model.KeyValueCollectionLoader;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlIdSerializer;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route("taskStatistics")
@UiController("TaskStatistics")
@UiDescriptor("task-statistics.xml")
public class TaskStatistics extends Screen {

    private Integer taskId;
    @Autowired
    private StatsService statsService;
    @Autowired
    private KeyValueCollectionLoader taskStatsDl;

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Subscribe
    public void onUrlParamsChanged(UrlParamsChangedEvent event) {
        taskId = (Integer)UrlIdSerializer.deserializeId(Integer.class, event.getParams().get("taskId"));
        taskStatsDl.load();
    }


    @Install(to = "taskStatsDl", target = Target.DATA_LOADER)
    private List<KeyValueEntity> taskStatsDlLoadDelegate(ValueLoadContext valueLoadContext) {
        return statsService.getProjectStatistics(taskId);
    }




}