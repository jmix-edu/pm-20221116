package com.company.pm.services;

import com.company.pm.entity.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PostService {

    public List<Post> fetchPosts(int amount) {
        RestTemplate rest = new RestTemplate();
        Post[] posts = rest.getForObject("http://jsonplaceholder.typicode.com/posts", Post[].class);
        return posts != null ? Arrays.asList(posts) : Collections.emptyList();
    }

}