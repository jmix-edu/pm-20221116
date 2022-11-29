package com.company.pm.services;

import com.company.pm.entity.User;
import com.company.pm.security.ProjectManagerRole;
import io.jmix.core.DataManager;
import io.jmix.core.UnconstrainedDataManager;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.securityui.role.UiMinimalRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private DataManager dataManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public User findLeastBusyUser() {
         User user = dataManager.loadValues("select u, count(t.id) " +
                 "from User u left outer join Task_ t " +
                 "on u = t.assignee " +
                 "group by u order by count(t.id)")
                 .properties("user", "tasks")
                 .list().stream().map(e -> e.<User>getValue("user"))
                 .findFirst()
                 .orElseThrow(IllegalStateException::new);
         return user;
    }

    @Transactional
    public User register(String username, String password) {
        UnconstrainedDataManager manager = dataManager.unconstrained();
        User user = manager.create(User.class);
        user.setUsername(username);
        user.setEmail(username+"@mail.com");
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(true);
        User saved = manager.save(user);

        RoleAssignmentEntity assignment = manager.create(RoleAssignmentEntity.class);
        assignment.setRoleCode(ProjectManagerRole.CODE);
        assignment.setUsername(user.getUsername());
        assignment.setRoleType(RoleAssignmentRoleType.RESOURCE);
        manager.save(assignment);

        RoleAssignmentEntity assignment2 = manager.create(RoleAssignmentEntity.class);
        assignment2.setRoleCode(UiMinimalRole.CODE);
        assignment2.setUsername(user.getUsername());
        assignment2.setRoleType(RoleAssignmentRoleType.RESOURCE);
        manager.save(assignment2);

        return saved;
    }

    @Transactional
    public void deleteInactiveUsers() {
        List<User> list = dataManager.
                loadValue("select u from User u where u.active = false", User.class)
                .list();
        dataManager.remove(list);
        //int i = entityManager.createNativeQuery("delete from user_ where active = false").executeUpdate();
        log.info(list.size()+" users were deleted");
    }
}