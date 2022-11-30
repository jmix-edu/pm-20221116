package com.company.pm.report;

import com.company.pm.entity.User;
import com.haulmont.yarg.formatters.CustomReport;
import com.haulmont.yarg.structure.BandData;
import com.haulmont.yarg.structure.Report;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class XDocReport implements CustomReport {
    @Override
    public byte[] createReport(Report report, BandData rootBand, Map<String, Object> params) {
        User user = (User)params.get("user");
        return "Hello %s".formatted(user.getDisplayName()).getBytes(StandardCharsets.UTF_8);
    }
}
