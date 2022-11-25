package com.company.pm.entity;

import com.company.pm.listener.TaskJpaCallbacks;
import io.jmix.core.DeletePolicy;
import io.jmix.core.FileRef;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.core.pessimisticlocking.PessimisticLock;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.Date;
import java.util.List;
import java.util.Set;

@JmixEntity
@PessimisticLock
@Table(name = "TASK_", indexes = {
        @Index(name = "IDX_TASK__ASSIGNEE", columnList = "ASSIGNEE_ID"),
        @Index(name = "IDX_TASK__PROJECT", columnList = "PROJECT_ID")
})
@Entity(name = "Task_")
@EntityListeners(TaskJpaCallbacks.class)
public class Task {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @JoinColumn(name = "ASSIGNEE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User assignee;

    @PositiveOrZero
    @Column(name = "HOURS_SPENT")
    private Integer hoursSpent;

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

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    @Column(name = "STATE")
    private String state;

    @OneToMany(mappedBy = "task")
    private Set<Subtask> subtasks;

    @PositiveOrZero
    @Column(name = "HOURS_PLANNED")
    private Integer hoursPlanned = 0;

    @Column(name = "ATTACHEMENT")
    private FileRef attachement;

    @JoinTable(name = "TASK_TAG_LINK",
            joinColumns = @JoinColumn(name = "TASK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<Tag> tags;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;


    @InstanceName
    @DependsOnProperties({"name"})
    public String getInstanceName() {
        return name;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Integer getHoursPlanned() {
        return hoursPlanned;
    }

    public void setHoursPlanned(Integer hoursPlanned) {
        this.hoursPlanned = hoursPlanned;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public FileRef getAttachement() {
        return attachement;
    }

    public void setAttachement(FileRef attachement) {
        this.attachement = attachement;
    }

    public Set<Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(Set<Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public TaskState getState() {
        return state == null ? null : TaskState.fromId(state);
    }

    public void setState(TaskState state) {
        this.state = state == null ? null : state.getId();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(Integer hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
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

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (hoursSpent > 0 && getState() != TaskState.STARTED) {
            setState(TaskState.STARTED);
        }
    }
}