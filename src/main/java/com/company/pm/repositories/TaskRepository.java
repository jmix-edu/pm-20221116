package com.company.pm.repositories;

import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import io.jmix.core.repository.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JmixDataRepository<Task, Integer> {

    List<Task> findByName(String name);

    List<Task> findByProject(Project project);

}
