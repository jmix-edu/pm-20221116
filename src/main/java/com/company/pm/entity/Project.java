package com.company.pm.entity;

import com.company.pm.validation.ProjectLabelsSize;
import com.company.pm.validation.TwoDatesOrder;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.validation.group.UiCrossFieldChecks;
import io.jmix.dynattr.model.Categorized;
import io.jmix.dynattr.model.Category;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@JmixEntity
@Table(name = "PROJECT", indexes = {
        @Index(name = "IDX_PROJECT_MANAGER", columnList = "MANAGER_ID"),
        @Index(name = "IDX_PROJECT_NAME", columnList = "NAME"),
        @Index(name = "IDX_PROJECT_START_DATE", columnList = "START_DATE"),
        @Index(name = "IDX_PROJECT_BUDGET", columnList = "BUDGET_ID"),
        @Index(name = "IDX_PROJECT_CATEGORY", columnList = "CATEGORY_ID")
})
@Entity
@TwoDatesOrder(firstDate = "startDate", lastDate = "endDate",
        dateClass = LocalDateTime.class,
        groups = {UiCrossFieldChecks.class})
public class Project implements Categorized {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @Length(message = "{msg://com.company.pm.entity/Project.name.validation.Length}", min = 3, max = 200)
    @InstanceName
    @Column(name = "NAME", nullable = false, length = 1024)
    @NotNull
    private String name;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @JoinColumn(name = "MANAGER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User manager;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Composition
    @OneToMany(mappedBy = "project")
    private Set<Task> tasks;

    @JoinColumn(name = "BUDGET_ID")
    @Composition
    @OneToOne(fetch = FetchType.LAZY)
    private ProjectBudget budget;

    @Column(name = "PROJECT_LABELS")
    @ProjectLabelsSize(min = 2, max = 5)
    private ProjectLabels projectLabels;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProjectLabels getProjectLabels() {
        return projectLabels;
    }

    public void setProjectLabels(ProjectLabels projectLabels) {
        this.projectLabels = projectLabels;
    }

    public ProjectBudget getBudget() {
        return budget;
    }

    public void setBudget(ProjectBudget budget) {
        this.budget = budget;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}