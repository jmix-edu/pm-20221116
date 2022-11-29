package com.company.pm.jobs;

import com.company.pm.services.UserCleaner;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CleaningJobConfiguration {

    @Bean
    public JobDetail registerCleaningJob() {
        return JobBuilder.newJob()
                .ofType(UserCleaner.class)
                .storeDurably()
                .withIdentity("inactiveUsersCleaning")
                .build();
    }

    @Bean
    public Trigger troggerCleaningJob() {
        return TriggerBuilder.newTrigger()
                .forJob(registerCleaningJob())
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .build();
    }

}
