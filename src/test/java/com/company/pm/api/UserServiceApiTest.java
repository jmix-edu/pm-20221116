package com.company.pm.api;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class UserServiceApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GraphQLTestTemplate testTemplate;

    @Test
    public void testLeastBusyUserApi() throws Exception {

        OauthHelper helper = new OauthHelper(mockMvc);

        GraphQLResponse graphQLResponse = testTemplate.withBearerAuth(helper.getAccessToken())
                .postForResource("com/company/pm/api/query-least-busy-user.gql");

        boolean hasUser = graphQLResponse.getRawResponse().getBody().contains("sanches");
        assertTrue("Should be sanches", hasUser);
    }

}
