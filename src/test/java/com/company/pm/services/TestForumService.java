package com.company.pm.services;

import com.company.pm.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;

//TODO
@RunWith(MockitoJUnitRunner.class)
public class TestForumService {

    @Mock
    private PostService postService;

    @Test
    void testSavePostsSuccessfully() {
        //Given
        Mockito.when(postService.fetchPosts(10)).thenReturn(Collections.emptyList());

        ForumService fs = new ForumService(postService);
        //When
        List<Post> posts = fs.savePosts(10);
        //Then
        Assertions.assertEquals(10, posts.size());
    }

    @Test
    void testSavePostsError() {
        //Given
        Mockito.when(postService.fetchPosts(5)).thenThrow(new TimeoutException());

        ForumService fs = new ForumService(postService);
        //When
        List<Post> posts = fs.savePosts(10);
        //Then
        Assertions.assertEquals(10, posts.size());
    }


}
