package com.company.pm.services;

import com.company.pm.entity.User;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserService {
    @Autowired
    private DataManager dataManager;

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
}