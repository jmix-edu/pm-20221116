package com.company.pm.services;

import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TaskService {

    private final DataManager dataManager;
    private final CurrentAuthentication currentAuthentication;

    public TaskService(DataManager dataManager, CurrentAuthentication currentAuthentication) {
        this.dataManager = dataManager;
        this.currentAuthentication = currentAuthentication;
    }

    public Task createTask(Project project, String taskName, LocalDateTime startDate) {
        Task task = dataManager.create(Task.class);
        task.setProject(project);
        task.setName(taskName);
        task.setStartDate(startDate);
        task.setAssignee((User)currentAuthentication.getUser());
        task.setHoursSpent(1);
        return dataManager.save(task);
    }

}