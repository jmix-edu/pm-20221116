package com.company.pm.security.ldap;

import com.company.pm.security.AnonymousRole;
import com.company.pm.security.FullAccessRole;
import com.company.pm.security.ProjectManagerRole;
import com.google.common.collect.ImmutableMap;
import io.jmix.ldap.userdetails.LdapAuthorityToJmixRoleCodesMapper;
import io.jmix.securityui.role.UiMinimalRole;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@Component
public class AppLdapRoleMapper implements LdapAuthorityToJmixRoleCodesMapper {

    private final static Map<String, String> roleCodes = ImmutableMap.of(
            "admin", FullAccessRole.CODE,
            "developers", "developer",
            "managers", ProjectManagerRole.CODE
    );

    @Override
    public Collection<String> mapAuthorityToJmixRoleCodes(String authority) {
        return Set.of(roleCodes.getOrDefault(authority, AnonymousRole.CODE), UiMinimalRole.CODE);
    }
}
