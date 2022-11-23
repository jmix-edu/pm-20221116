package com.company.pm.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.Date;

@JmixEntity
@Table(name = "PROJECT_BUDGET")
@Entity
public class ProjectBudget {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private Integer id;

    @Positive(message = "Project budget is wrong: ${validatedValue}. Should be positive")
    @Column(name = "INITIAL_BUDGET", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal initialBudget;

    @PositiveOrZero
    @Column(name = "SPENT_BUDGET", precision = 19, scale = 2)
    private BigDecimal spentBudget;


    @InstanceName
    @DependsOnProperties({"initialBudget", "spentBudget"})
    public String getBudgetInfo() {
        return initialBudget + " " + spentBudget;
    }

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    public BigDecimal getSpentBudget() {
        return spentBudget;
    }

    public void setSpentBudget(BigDecimal spentBudget) {
        this.spentBudget = spentBudget;
    }

    public BigDecimal getInitialBudget() {
        return initialBudget;
    }

    public void setInitialBudget(BigDecimal initialBudget) {
        this.initialBudget = initialBudget;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}