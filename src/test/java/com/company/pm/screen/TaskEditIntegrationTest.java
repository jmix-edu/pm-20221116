package com.company.pm.screen;

import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.entity.User;
import com.company.pm.screen.task.TaskEdit;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.testassist.junit.UiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.UUID;

@UiTest(mainScreenId = "MainScreen")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class TaskEditIntegrationTest {

    @Autowired
    private Metadata metadata;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private ScreenBuilders screenBuilders;

    @Test
    @DisplayName("Check the computation of the least busy user")
    protected void openTaskEdit(Screens screens) {
        //Given
        List<User> users = generateTestData();

        //When
        TaskEdit taskEdit = screenBuilders.editor(Task.class, screens.getOpenedScreens().getRootScreen())
                .withScreenClass(TaskEdit.class)
                .newEntity()
                .show();
        //Then
        Assertions.assertEquals(users.get(0), taskEdit.getEditedEntity().getAssignee());
    }

    private List<User> generateTestData() {
        User user1 = metadata.create(User.class);
        user1.setUsername("user1");

        User user2 = metadata.create(User.class);
        user2.setUsername("user2");

        Project project = dataManager.create(Project.class);
        project.setName("Db integration test");
        project.setManager(user1);

        Task task = dataManager.create(Task.class);
        task.setName("Write integration test");
        task.setAssignee(user2);
        task.setProject(project);
        task.setHoursSpent(1);

        Task task1 = dataManager.create(Task.class);
        task1.setName("Run integration test");
        task1.setAssignee(user2);
        task1.setProject(project);
        task1.setHoursSpent(1);

        Task task2 = dataManager.create(Task.class);
        task2.setName("Check integration test");
        task2.setAssignee(dataManager.unconstrained().load(User.class) //assigning admin
                .id(UUID.fromString("60885987-1b61-4247-94c7-dff348347f93")).one());
        task2.setProject(project);
        task2.setHoursSpent(1);

        dataManager.unconstrained().save(user1, user2, project, task, task1, task2);

        return List.of(user1, user2);
    }
}
