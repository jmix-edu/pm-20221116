package com.company.pm.listener;

import com.company.pm.entity.Task;
import org.atmosphere.config.service.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PostUpdate;

public class TaskJpaCallbacks {

    private static final Logger log = LoggerFactory.getLogger(TaskJpaCallbacks.class);

    @PostUpdate
    public void postTaskUpdate(Task task) {
        log.info("Task: "+task+" was updated");
    }

}
