package com.company.pm.services;

import io.jmix.core.security.SystemAuthenticator;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCleaner implements Job {
    @Autowired
    private UserService userService;
    @Autowired
    private SystemAuthenticator systemAuthenticator;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        systemAuthenticator.withSystem(() -> {
            userService.deleteInactiveUsers();
            return null;
        });

    }
}
