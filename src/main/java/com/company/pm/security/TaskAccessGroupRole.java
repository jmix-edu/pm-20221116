package com.company.pm.security;

import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "TaskAccessGroup", code = "task-access-group")
public interface TaskAccessGroupRole extends TaskAccessRole, ProjectAccessRole{
}