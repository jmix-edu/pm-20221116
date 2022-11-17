package com.company.pm.screen.task;

import io.jmix.core.FileRef;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.LinkButton;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Downloader downloader;

    @Install(to = "tasksTable.attachement", subject = "columnGenerator")
    private Component tasksTableAttachmentColumnGenerator(Task task) {
        FileRef fileRef = task.getAttachement();
        if (fileRef != null) {
            LinkButton linkButton = uiComponents.create(LinkButton.class);
            linkButton.setAction(new BaseAction("download")
                    .withCaption(fileRef.getFileName())
                    .withHandler(e -> downloader.download(fileRef)));
            return linkButton;
        } else {
            return null;
        }
    }

}