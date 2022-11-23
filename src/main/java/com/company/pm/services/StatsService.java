package com.company.pm.services;

import com.company.pm.dtos.ProjectStats;
import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.repositories.TaskRepository;
import io.jmix.core.DataManager;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class StatsService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private TaskRepository taskRepository;

    public List<ProjectStats> getProjectStats() {
        List<Project> projects = dataManager.
                load(Project.class).
                all().
                hint(PersistenceHints.SOFT_DELETION, false).
                fetchPlan("project-with-tasks").list();

        List<ProjectStats> projectStats = projects.stream().map(p -> {
            ProjectStats ps = dataManager.create(ProjectStats.class);
            ps.setId(p.getId());
            ps.setName(p.getName());
            Set<Task> tasks = p.getTasks();
            ps.setTasksCount(tasks.size());
            return ps;
        }).toList();
        return projectStats;
    }

}