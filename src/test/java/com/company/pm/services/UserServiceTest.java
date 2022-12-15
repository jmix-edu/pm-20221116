package com.company.pm.services;

import com.company.pm.Pm20221116Application;
import com.company.pm.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @Autowired
    private Metadata metadata;

    @Autowired
    private UserService userService;

    @MockBean
    private DataManager dataManager;

    @Test
    @DisplayName("For two users we should select only one least busy")
    public void testLeastBusyUserOfTwoUsers() {
        //Given
        User user1 = metadata.create(User.class);
        user1.setUsername("user1");
        KeyValueEntity entity1 = metadata.create(KeyValueEntity.class);
        entity1.setValue("user", user1);
        entity1.setValue("tasks", 1);

        User user2 = metadata.create(User.class);
        user2.setUsername("user1");
        KeyValueEntity entity2 = metadata.create(KeyValueEntity.class);
        entity2.setValue("user", user2);
        entity2.setValue("tasks", 10);

        List<KeyValueEntity> dataManagerResult = List.of(entity1, entity2);

        //TODO See service reimplemented
        Mockito.when(dataManager.loadValues(Mockito.anyString())
                .properties("user", "tasks").list()).thenReturn(dataManagerResult);
        //Then
        User leastBusyUser = userService.findLeastBusyUser();
        //When
        assertEquals("user1", leastBusyUser.getUsername());

    }

    @Test
    @DisplayName("Test that email for new users generated correctly")
    public void testUserEmailGeneration() {
        //Given
        String username = "TestUser";
        String password = "Password";

        User value = metadata.create(User.class);

        Mockito.when(dataManager.unconstrained()).thenReturn(dataManager);
        Mockito.when(dataManager.create(User.class)).thenReturn(value);
        Mockito.when(dataManager.save(ArgumentMatchers.any(User.class))).thenReturn(value);

        Mockito.when(dataManager.create(RoleAssignmentEntity.class)).thenReturn(metadata.create(RoleAssignmentEntity.class));
        Mockito.when(dataManager.save(ArgumentMatchers.any(RoleAssignmentEntity.class))).thenReturn(null);

        //When
        User user = userService.register(username, password);

        //Then
        assertEquals("TestUser@mail.com", user.getEmail());

    }

}
