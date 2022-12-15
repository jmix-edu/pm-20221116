package com.company.pm.services;

import com.company.pm.entity.Post;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    private final PostService postService;

    public ForumService(PostService postService) {
        this.postService = postService;
    }

    public List<Post> savePosts(int postCount) {
        List<Post> posts = postService.fetchPosts(postCount);
        //do somethig here
        return posts;
    }

}
