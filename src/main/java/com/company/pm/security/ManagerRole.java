package com.company.pm.security;

import com.company.pm.dtos.ProjectStats;
import com.company.pm.entity.Project;
import com.company.pm.entity.Subtask;
import com.company.pm.entity.Task;
import com.company.pm.entity.User;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Manager", code = "manager", scope = "UI", description = "Project manager, responsible for tasks")
public interface ManagerRole {
    @MenuPolicy(menuIds = {"Task_.browse", "Subtask.browse", "User.browse", "ProjectStats.browse", "Project.browse", "report_Report.run"})
    @ScreenPolicy(screenIds = {"Project.browse", "Task_.browse", "Subtask.browse", "User.browse", "ProjectStats.browse", "Project.edit", "Task_.edit", "User.edit", "report_Report.run", "report_InputParameters.dialog", "report_InputParameters.fragment"})
    void screens();

    @EntityAttributePolicy(entityClass = Project.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Project.class, actions = EntityPolicyAction.ALL)
    void project();

    @EntityPolicy(entityClass = ProjectStats.class, actions = EntityPolicyAction.READ)
    @EntityAttributePolicy(entityClass = ProjectStats.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void projectStats();

    @EntityAttributePolicy(entityClass = Subtask.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Subtask.class, actions = EntityPolicyAction.READ)
    void subtask();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.ALL)
    void task();

    @EntityAttributePolicy(entityClass = User.class, attributes = {"firstName", "lastName", "avatar"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityAttributePolicy(entityClass = User.class, attributes = {"id", "version", "username", "password", "email", "active", "timeZoneId"}, action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.UPDATE, EntityPolicyAction.READ})
    void user();

}