package com.company.pm.security;

import com.company.pm.entity.Task;
import com.company.pm.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;

@RowLevelRole(name = "TaskAccess", code = "task-access")
public interface TaskAccessRole {

    @PredicateRowLevelPolicy(entityClass = Task.class, actions = RowLevelPolicyAction.DELETE)
    default RowLevelBiPredicate<Task, ApplicationContext> taskPredicate(CurrentAuthentication auth) {
        return (task, applicationContext) -> task.getCreatedBy().equals(((User)auth.getUser()).getUsername());
    }

    @JpqlRowLevelPolicy(entityClass = Task.class,
            join = "join {E}.project proj",
            where = "proj.manager.id = :current_user_id or {E}.createdBy = :current_user_username")
    void task();
}