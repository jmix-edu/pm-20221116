package com.company.pm.services;

import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.security.Authenticated;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class UserServiceIntegrationTest {
    @Autowired
    private Metadata metadata;

    @Autowired
    private UserService userService;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private SystemAuthenticator systemAuthenticator;

    @Test
    @DisplayName("For two users we should select only one least busy")
    public void testLeastBusyUserOfTwoUsers() {
        //Given
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
        task1.setName("Write integration test");
        task1.setAssignee(user2);
        task1.setProject(project);
        task1.setHoursSpent(1);

        User user = systemAuthenticator.withUser("admin", () -> {
                    dataManager.save(user1, user2, project, task, task1);
                    return userService.findLeastBusyUser();
                }
        );

        assertEquals("user1", user.getUsername());


    }


}
