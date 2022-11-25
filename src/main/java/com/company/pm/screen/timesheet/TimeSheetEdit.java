package com.company.pm.screen.timesheet;

import com.company.pm.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.screen.*;
import com.company.pm.entity.TimeSheet;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("TimeSheet.edit")
@UiDescriptor("time-sheet-edit.xml")
@EditedEntityContainer("timeSheetDc")
public class TimeSheetEdit extends StandardEditor<TimeSheet> {
    @Autowired
    private CurrentAuthentication currentAuthentication;

    @Subscribe
    public void onInitEntity(InitEntityEvent<TimeSheet> event) {
        event.getEntity().setEmployee((User)currentAuthentication.getUser());
    }
}