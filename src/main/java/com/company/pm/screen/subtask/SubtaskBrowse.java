package com.company.pm.screen.subtask;

import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Subtask;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Subtask.browse")
@UiDescriptor("subtask-browse.xml")
@LookupComponent("subtasksTable")
public class SubtaskBrowse extends StandardLookup<Subtask> {
    @Autowired
    private CollectionLoader<Subtask> subtasksDl;

    @Subscribe
    public void onInit(InitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof SubtaskScreenOptions screenOptions) {
            subtasksDl.setParameter("taskId", screenOptions.getTaskId());
        } else {
            subtasksDl.removeParameter("taskId");
        }

    }



}