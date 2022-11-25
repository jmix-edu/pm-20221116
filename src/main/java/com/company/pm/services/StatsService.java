package com.company.pm.services;

import com.company.pm.dtos.ProjectStats;
import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.repositories.TaskRepository;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlanRepository;
import io.jmix.core.FetchPlans;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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