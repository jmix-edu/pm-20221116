package com.company.pm.jobs;

import com.company.pm.services.UserCleaner;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class CleaningJobConfiguration {

    public JobDetail registerCleaningJob() {
        return JobBuilder.newJob()
                .ofType(UserCleaner.class)
                .storeDurably()
                .withIdentity("inactiveUsersCleaning")
                .build();
    }

    public Trigger troggerCleaningJob() {
        return TriggerBuilder.newTrigger()
                .forJob(registerCleaningJob())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .build();
    }

}
