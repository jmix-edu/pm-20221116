package com.company.pm.security;

import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.RowLevelRole;
import io.jmix.securityui.role.UiMinimalRole;

@ResourceRole(name = "ProjectManager", code = "project-manager", scope = "UI")
public interface ProjectManagerRole extends ManagerRole, UiMinimalRole {
}