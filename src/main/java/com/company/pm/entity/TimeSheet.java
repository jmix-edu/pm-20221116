package com.company.pm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.metamodel.annotation.JmixProperty;
import io.jmix.core.metamodel.annotation.Store;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@JmixEntity
@Store(name = "ts")
@Table(name = "TIME_SHEET")
@Entity
public class TimeSheet {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @SystemLevel
    @Column(name = "TASK_ID")
    private Integer taskId;

    @DependsOnProperties({"taskId"})
    @JmixProperty
    @Transient
    private Task task;

    @SystemLevel
    @Column(name = "EMPLOYEE_ID")
    private UUID employeeId;

    @DependsOnProperties({"employeeId"})
    @JmixProperty
    @Transient
    private User employee;

    @Positive
    @Column(name = "WORK_HOURS", nullable = false)
    @NotNull
    private Integer workHours;

    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(UUID employeeId) {
        this.employeeId = employeeId;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}