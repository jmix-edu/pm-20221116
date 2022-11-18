package com.company.pm.security;

import com.company.pm.entity.Project;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "ProjectAccess", code = "project-access")
public interface ProjectAccessRole {

    @JpqlRowLevelPolicy(entityClass = Project.class, where = "{E}.manager.id = :current_user_id")
    void project();



}