package com.company.pm.screen.taskstatistics;

import com.company.pm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.FluentValuesLoader;
import io.jmix.core.ValueLoadContext;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UiController("TaskStatistics")
@UiDescriptor("task-statistics.xml")
public class TaskStatistics extends Screen {

    @Autowired
    private DataManager dataManager;

    @Install(to = "taskStatsDl", target = Target.DATA_LOADER)
    private List<KeyValueEntity> taskStatsDlLoadDelegate(ValueLoadContext valueLoadContext) {
//                <property name="id"/>
//                <property name="name"/>
//                <property name="subtasksCount"/>
        KeyValueEntity kve = dataManager.create(KeyValueEntity.class);
        kve.setValue("id", 1);
        kve.setValue("name", "Test");
        kve.setValue("subtasksCount", 100);
        kve.setValue("extraProperty", "New work");

        List<KeyValueEntity> tasks = dataManager.loadValues("select t.id, t.name, count(t.subtasks) from Task_ t group by t.id, t.name")
                .properties("id", "name", "subtasksCount").list();

        List<KeyValueEntity> keyValueEntities = new ArrayList<>(tasks);
        keyValueEntities.add(kve);
        return keyValueEntities;
    }



}