package com.company.pm.screen.subtask;

import io.jmix.ui.screen.*;
import com.company.pm.entity.Subtask;

@UiController("Subtask.browse")
@UiDescriptor("subtask-browse.xml")
@LookupComponent("subtasksTable")
public class SubtaskBrowse extends StandardLookup<Subtask> {
}