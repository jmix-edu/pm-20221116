package com.company.pm.screen.main;

import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.entity.TimeSheet;
import com.company.pm.entity.User;
import com.company.pm.screen.task.TaskEdit;
import com.company.pm.services.TaskService;
import io.jmix.core.DataManager;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.ScreenTools;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.component.mainwindow.Drawer;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@UiController("MainScreen")
@UiDescriptor("main-screen.xml")
@Route(path = "main", root = true)
public class MainScreen extends Screen implements Window.HasWorkArea {
    @Autowired
    private CollectionLoader<Project> projectsDl;

    @Autowired
    private ScreenTools screenTools;

    @Autowired
    private AppWorkArea workArea;
    @Autowired
    private Drawer drawer;
    @Autowired
    private Button collapseDrawerButton;
    @Autowired
    private CollectionLoader<Task> tasksDl;
    @Autowired
    private TaskService taskService;
    @Autowired
    private EntityComboBox<Project> projectSelector;
    @Autowired
    private TextField<String> nameSelector;
    @Autowired
    private DateField<LocalDateTime> dateSelector;
    @Autowired
    private Notifications notifications;
    @Autowired
    private CollectionContainer<Task> tasksDc;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private InstanceContainer<Task> taskDc;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private DataManager dataManager;


    @Override
    public AppWorkArea getWorkArea() {
        return workArea;
    }

    @Subscribe("collapseDrawerButton")
    private void onCollapseDrawerButtonClick(Button.ClickEvent event) {
        drawer.toggle();
        if (drawer.isCollapsed()) {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_RIGHT);
        } else {
            collapseDrawerButton.setIconFromSet(JmixIcon.CHEVRON_LEFT);
        }
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        screenTools.openDefaultScreen(
                UiControllerUtils.getScreenContext(this).getScreens());
        screenTools.handleRedirect();

        createNewTask();
    }

    private void createNewTask() {
        Task task = dataContext.create(Task.class);
        taskDc.setItem(task);

        TimeSheet timeSheet = dataManager.create(TimeSheet.class);
        timeSheet = dataContext.merge(timeSheet);
        timeSheet.setTaskId(task.getId());
        timeSheet.setTask(task);
    }

    @Subscribe("addTaskBtn")
    public void onAddTaskBtnClick(Button.ClickEvent event) {
        Task item = taskDc.getItem();
        item.setAssignee((User)currentAuthentication.getUser());
        item.setHoursSpent(1);

        dataContext.getModified().forEach(e -> {
            if (e instanceof TimeSheet ts) {
                ts.setEmployee((User)currentAuthentication.getUser());
                ts.setWorkHours(item.getHoursSpent());
            }
        });

        dataContext.commit().forEach(e -> {
            if (e instanceof Task task) {
                tasksDc.replaceItem(task);
                notifications.create(Notifications.NotificationType.HUMANIZED)
                        .withCaption("<h2>The task is saved<h2>")
                        .withDescription(task.getTaskCaption())
                        .withContentMode(ContentMode.HTML)
                        .show();
            }
        });

        createNewTask();
        //addTaskWithService();
    }

    private void addTaskWithService() {
        if (projectSelector.getValue() == null
        || nameSelector.getValue() == null
        || dateSelector.getValue() == null
        ) {
            notifications.create(Notifications.NotificationType.ERROR)
                    .withCaption("Please fill all fields")
                    .show();
            return;
        }
        Task task = taskService.createTask(projectSelector.getValue(), nameSelector.getValue(), dateSelector.getValue());
        tasksDc.replaceItem(task);

        projectSelector.setValue(null);
        nameSelector.setValue(null);
        dateSelector.setValue(null);
    }

    @Subscribe("refresh")
    public void onRefresh(Action.ActionPerformedEvent event) {
        getScreenData().loadAll();
    }

    @Subscribe("tasksCalendar")
    public void onTasksCalendarCalendarEventClick(Calendar.CalendarEventClickEvent<Task> event) {
        Task task = (Task) event.getEntity();
        if (task == null) return;
        TaskEdit editor = screenBuilders.editor(Task.class, this)
                .withScreenClass(TaskEdit.class)
                .editEntity(task)
                .withOpenMode(OpenMode.DIALOG)
                .build();

        editor.addAfterCloseListener(closeEvent -> {
            if (closeEvent.closedWith(StandardOutcome.COMMIT)) {
                tasksDc.replaceItem(editor.getEditedEntity());

            }
        });

        editor.show();
    }

    @Install(to = "nameSelector", subject = "formatter")
    private String nameSelectorFormatter(String value) {
        if (value == null) return null;
        return value.toUpperCase();
    }
}
