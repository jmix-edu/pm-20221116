package com.company.pm.screen.task;

import com.company.pm.entity.Task;
import com.company.pm.entity.TaskState;
import com.company.pm.entity.User;
import com.company.pm.services.UserService;
import io.jmix.ui.component.Button;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {

    @Autowired
    private UserService userService;

    @Autowired
    private Button startTaskBtn;


    @Subscribe
    public void onInitEntity(InitEntityEvent<Task> event) {
        User user = userService.findLeastBusyUser();
        event.getEntity().setAssignee(user);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        startTaskBtn.setEnabled(getEditedEntity().getState() != TaskState.STARTED);
    }

    @Subscribe("startTaskBtn")
    public void onStartTaskBtnClick(Button.ClickEvent event) {
        Task task = getEditedEntity();
        task.setHoursSpent(1);
        task.setState(TaskState.STARTED);
        startTaskBtn.setEnabled(false);
    }


}