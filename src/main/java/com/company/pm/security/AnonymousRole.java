package com.company.pm.security;

import com.company.pm.dtos.ProjectStats;
import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Anonymous", code = AnonymousRole.CODE, scope = "UI")
public interface AnonymousRole {

    public static String CODE = "anonymous";

    @ScreenPolicy(screenIds = {"ProjectStats.browse", "RegistrationScreen"})
    void screens();

    @EntityAttributePolicy(entityClass = ProjectStats.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = ProjectStats.class, actions = EntityPolicyAction.READ)
    void projectStats();

    @EntityAttributePolicy(entityClass = Project.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Project.class, actions = EntityPolicyAction.READ)
    void project();

    @EntityAttributePolicy(entityClass = Task.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Task.class, actions = EntityPolicyAction.READ)
    void task();
}