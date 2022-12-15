package com.company.pm.services;

import com.company.pm.entity.ProjectUserInfo;
import io.jmix.graphql.service.UserInfo;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import org.springframework.stereotype.Service;

@GraphQLApi
@Service
public class TaskGraphQLService {
    private final UserService userService;

    public TaskGraphQLService(UserService userService) {
        this.userService = userService;
    }

    @GraphQLQuery(name = "leastBusyUser")
    public UserInfo findLeastBusyUser() {
        return new UserInfo(userService.findLeastBusyUser());
    }

}
