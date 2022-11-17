package com.company.pm.dtos;

import io.jmix.core.entity.annotation.JmixId;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

@JmixEntity
public class ProjectStats {
    @JmixId
    private Integer id;

    @InstanceName
    private String name;

    private Integer tasksCount;

    private Integer hoursPlanned;

    private Integer hoursSpent;

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public Integer getHoursPlanned() {
        return hoursPlanned;
    }

    public void setHoursPlanned(Integer hoursPlanned) {
        this.hoursPlanned = hoursPlanned;
    }

    public Integer getTasksCount() {
        return tasksCount;
    }

    public void setTasksCount(Integer tasksCount) {
        this.tasksCount = tasksCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}