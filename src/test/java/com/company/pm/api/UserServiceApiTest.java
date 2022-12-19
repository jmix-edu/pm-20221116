package com.company.pm.api;

import com.company.pm.entity.Project;
import com.company.pm.entity.Task;
import com.company.pm.entity.User;
import com.company.pm.services.UserService;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import io.jmix.core.DataManager;
import io.jmix.core.Metadata;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class UserServiceApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GraphQLTestTemplate testTemplate;

    @Autowired
    private Metadata metadata;

    @Autowired
    private UserService userService;

    @Autowired
    private DataManager dataManager;

    @Autowired
    private SystemAuthenticator systemAuthenticator;


    @Test
    public void testLeastBusyUserApi() throws Exception {

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

        //When
        OauthHelper helper = new OauthHelper(mockMvc);
        GraphQLResponse graphQLResponse = testTemplate.withBearerAuth(helper.getAccessToken())
                .postForResource("com/company/pm/api/query-least-busy-user.gql");

        //Then
        boolean hasUser = graphQLResponse.getRawResponse().getBody().contains("user1");
        assertTrue("Should be user1", hasUser);
    }

}
