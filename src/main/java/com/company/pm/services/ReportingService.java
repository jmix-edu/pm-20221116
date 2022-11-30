package com.company.pm.services;

import com.haulmont.yarg.reporting.ReportOutputDocument;
import io.jmix.reports.entity.ReportOutputType;
import io.jmix.reports.runner.ReportRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class ReportingService {

    @Autowired
    private ReportRunner reportRunner;
    @Autowired
    private UserService userService;

    public void runReport() {
        ReportOutputDocument report = reportRunner.byReportCode("user-cv")
                .withOutputType(ReportOutputType.PDF)
                .withTemplateCode("DEFAULT")
                .addParam("user", userService.findLeastBusyUser())
                .run();

        String userHome = System.getProperty("user.home");
        File f = new File(userHome, report.getDocumentName());
        try (FileOutputStream fos = new FileOutputStream(f)) {
            fos.write(report.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}