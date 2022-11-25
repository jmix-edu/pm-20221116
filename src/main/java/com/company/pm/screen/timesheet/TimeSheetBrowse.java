package com.company.pm.screen.timesheet;

import io.jmix.ui.screen.*;
import com.company.pm.entity.TimeSheet;

@UiController("TimeSheet.browse")
@UiDescriptor("time-sheet-browse.xml")
@LookupComponent("timeSheetsTable")
public class TimeSheetBrowse extends StandardLookup<TimeSheet> {
}