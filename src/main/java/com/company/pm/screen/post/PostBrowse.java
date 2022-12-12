package com.company.pm.screen.post;

import com.company.pm.services.PostService;
import io.jmix.core.LoadContext;
import io.jmix.core.SaveContext;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@UiController("Post.browse")
@UiDescriptor("post-browse.xml")
@LookupComponent("postsTable")
public class PostBrowse extends StandardLookup<Post> {
    private static final Logger log = LoggerFactory.getLogger(PostBrowse.class);

    @Autowired
    private PostService postService;
    @Autowired
    private DataContext dataContext;

    @Install(to = "postsDl", target = Target.DATA_LOADER)
    private List<Post> postsDlLoadDelegate(LoadContext<Post> loadContext) {
        return postService.fetchPosts();
    }

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> commitDelegate(SaveContext saveContext) {
        saveContext.getEntitiesToRemove().forEach( e -> {
            log.warn(e.toString());
        });
        return null;
    }

    @Subscribe("postsTable.save")
    public void onPostsTableSave(Action.ActionPerformedEvent event) {
        dataContext.commit();
    }
}