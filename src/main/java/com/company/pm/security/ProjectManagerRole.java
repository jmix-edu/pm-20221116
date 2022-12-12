package com.company.pm.security;

import io.jmix.reportsui.role.ReportsRunRole;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.UiMinimalRole;

@ResourceRole(name = "ProjectManager", code = ProjectManagerRole.CODE, scope = "UI")
public interface ProjectManagerRole extends ManagerRole, UiMinimalRole, ReportsRunRole {

    public static final String CODE = "project-manager";
    @SpecificPolicy(resources = "jmix.pm.project.archive")
    void specific();
}