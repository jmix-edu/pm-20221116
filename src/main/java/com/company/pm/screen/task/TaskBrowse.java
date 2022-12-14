package com.company.pm.screen.task;

import com.company.pm.screen.filter.SearchEvent;
import com.company.pm.screen.subtask.SubtaskBrowse;
import com.company.pm.screen.subtask.SubtaskScreenOptions;
import com.company.pm.screen.taskstatistics.TaskStatistics;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.Messages;
import io.jmix.core.SaveContext;
import io.jmix.data.PersistenceHints;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.*;
import io.jmix.ui.download.Downloader;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Task;
import io.jmix.ui.screen.LookupComponent;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.browse")
@UiDescriptor("task-browse.xml")
@LookupComponent("tasksTable")
public class TaskBrowse extends StandardLookup<Task> {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Downloader downloader;
    @Autowired
    private Messages messages;
    @Autowired
    private GroupTable<Task> tasksTable;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private ScreenFacet<TaskStatistics> taskStats;
    @Autowired
    private Notifications notifications;

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

    @Install(to = "tasksTable.taskInfo", subject = "columnGenerator")
    private Component tasksTableTaskInfoColumnGenerator(Task task) {
        Label<String> label = uiComponents.create(Label.class);
        String s = "dd %s".formatted(task.getState());
        label.setValue(messages.formatMessage(getClass(), "task.info.message", task.getState(), task.getHoursPlanned()));
        return label;
    }

    @Subscribe(id = "filterFragment", target = Target.CONTROLLER)
    protected void onFilter(SearchEvent event) {
        notifications.create().withCaption(event.getSearchText()).show();
    }

    @Subscribe("hardDeleteBtn")
    public void onHardDeleteBtnClick(Button.ClickEvent event) {
        Task singleSelected = tasksTable.getSingleSelected();
        dataManager.save(
                new SaveContext()
                        .removing(singleSelected)
                        .setHint(PersistenceHints.SOFT_DELETION, false)
        );
        tasksDl.load();
    }

    @Subscribe("showStats")
    public void onShowStats(Action.ActionPerformedEvent event) {
        taskStats.show();
    }

    @Subscribe("subtasksButton")
    public void onSubtasksButtonClick(Button.ClickEvent event) {
        Task task = tasksTable.getSingleSelected();
        if (task == null) return;
        SubtaskBrowse build = screenBuilders.screen(this)
                .withScreenClass(SubtaskBrowse.class)
                .withOptions(new SubtaskScreenOptions(task.getId()))
                .withOpenMode(OpenMode.DIALOG)
                .build();
        build.show();
    }

    @Install(to = "showStats", subject = "enabledRule")
    private boolean showStatsEnabledRule() {
        return tasksTable.getSingleSelected() != null;
    }

    @Install(to = "taskStats", subject = "screenConfigurer")
    private void taskStatsScreenConfigurer(TaskStatistics taskStatistics) {
        Task task = tasksTable.getSingleSelected();
        taskStatistics.setTaskId(task != null ? task.getId() : -1);
    }

}