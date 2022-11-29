package com.company.pm.security.ldap;

import io.jmix.ldap.userdetails.LdapUserAdditionalRoleProvider;
import io.jmix.security.authentication.RoleGrantedAuthority;
import io.jmix.security.model.RowLevelRole;
import io.jmix.security.role.RowLevelRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class AppLdapRowLevelProvider implements LdapUserAdditionalRoleProvider {
    @Autowired
    private RowLevelRoleRepository rowLevelRoleRepository;
    @Override
    public Set<GrantedAuthority> getAdditionalRoles(DirContextOperations user, String username) {
        String[] roleCodes = user.getStringAttributes("employeeType");
        if (roleCodes == null || roleCodes.length == 0) {
            return Collections.emptySet();
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (String role: roleCodes) {
            RowLevelRole roleByCode = rowLevelRoleRepository.getRoleByCode(role);
            RoleGrantedAuthority authority = RoleGrantedAuthority.ofRowLevelRole(roleByCode);
            authorities.add(authority);
        }

        return authorities;
    }
}
