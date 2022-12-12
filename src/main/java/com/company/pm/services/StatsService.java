package com.company.pm.services;

import com.company.pm.dtos.ProjectStats;
import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.repositories.TaskRepository;
import io.jmix.core.*;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class StatsService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private TaskRepository taskRepository;

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private FetchPlanRepository fetchPlanRepository;
    @Autowired
    private FetchPlans fetchPlans;

    public List<KeyValueEntity> getProjectStatistics(Integer taskId) {
        KeyValueEntity kve = dataManager.create(KeyValueEntity.class);
        kve.setValue("id", 1);
        kve.setValue("name", "Test");
        kve.setValue("subtasksCount", 100);
        kve.setValue("extraProperty", "New work");

        FluentValuesLoader loader = dataManager.loadValues("select t.id, t.name, count(t.subtasks) " +
                        "from Task_ t " +
                        "where t.id = :taskId or :taskId is null " +
                        "group by t.id, t.name")
                .properties("id", "name", "subtasksCount");
        loader.parameter("taskId", taskId);
        List<KeyValueEntity> tasks = loader.list();

        List<KeyValueEntity> keyValueEntities = new ArrayList<>(tasks);
        keyValueEntities.add(kve);
        return keyValueEntities;
    }

    public List<ProjectStats> getProjectStats() {

        FetchPlan fetchPlan1 = fetchPlanRepository.getFetchPlan(Project.class, "project-with-tasks");

        FetchPlan fetchPlan = fetchPlans.builder(Project.class)
                .addFetchPlan(fetchPlan1)
                .add("startDate")
                .build();

        List<Project> projects = dataManager.
                load(Project.class).
                all().
                cacheable(true).
                hint(PersistenceHints.SOFT_DELETION, false).
                fetchPlan(fetchPlan).list();

        List<ProjectStats> projectStats = projects.stream().map(p -> {
            ProjectStats ps = dataManager.create(ProjectStats.class);
            ps.setId(p.getId());
            ps.setName(p.getName());
            ps.setStartDate(p.getStartDate());
            Set<Task> tasks = p.getTasks();
            ps.setTasksCount(tasks.size());
            return ps;
        }).toList();
        return projectStats;
    }

    @Transactional
    public Project getProjectById(Integer id) {
        FetchPlan fetchPlan = fetchPlans.builder(Project.class)
                .add("endDate")
                .add("tasks")
                .partial(true)
                .build();
        Map<String, Object> hints = PersistenceHints.builder().withFetchPlan(fetchPlan).build();
        Project project = em.find(Project.class, id, hints);
        return project;
    }

}