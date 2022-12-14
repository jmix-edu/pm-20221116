package com.company.pm.screen.task;

import com.company.pm.entity.TaskState;
import com.company.pm.entity.User;
import com.company.pm.services.UserService;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.UiComponentsGenerator;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Task_.edit")
@UiDescriptor("task-edit.xml")
@EditedEntityContainer("taskDc")
public class TaskEdit extends StandardEditor<Task> {

    @Autowired
    private UserService userService;

    @Autowired
    private Button startTaskBtn;

    @Autowired
    private HBoxLayout buttonsBox;
    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        Button button = uiComponents.create(Button.class);
        button.setCaption("New Button");
        buttonsBox.add(button);
    }


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