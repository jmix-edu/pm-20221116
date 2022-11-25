package com.company.pm.listener;

import com.company.pm.entity.Project;
import com.company.pm.entity.Subtask;
import com.company.pm.entity.Task;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.core.SaveContext;
import io.jmix.core.entity.EntityPropertyChangeEvent;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntityLoadingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class TaskEventListener {
    private static final Logger log = LoggerFactory.getLogger(TaskEventListener.class);
    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onTaskChangedBeforeCommit(EntityChangedEvent<Task> event) {
        Object hoursSpent = event.getChanges().getOldValue("hoursSpent");
        log.info("EntityChangedEvent: hoursSpent: "+ hoursSpent);
    }

    @EventListener
    public void onTaskLoading(EntityLoadingEvent<Task> event) {
        log.info("Task is loading: "+event.getEntity());
    }

    @EventListener
    public void onSubTaskLoading(EntityLoadingEvent<Subtask> event) {
        log.info("Subtask is loading: "+event.getEntity());
    }

    @TransactionalEventListener
    public void onTaskChangedAfterCommit(EntityChangedEvent<Task> event) {
        log.info("Saving project after task is committed: "+event.getEntityId());
        Project project = null;
        dataManager.save(new SaveContext()
                .setJoinTransaction(false)
                .saving(project));
    }


}