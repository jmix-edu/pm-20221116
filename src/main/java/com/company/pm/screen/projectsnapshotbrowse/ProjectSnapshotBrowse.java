package com.company.pm.screen.projectsnapshotbrowse;

import com.company.pm.entity.Project;
import io.jmix.auditui.screen.snapshot.SnapshotDiffViewer;
import io.jmix.ui.component.Table;
import io.jmix.ui.screen.Screen;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

@UiController("ProjectSnapshotBrowse")
@UiDescriptor("project-snapshot-browse.xml")
public class ProjectSnapshotBrowse extends Screen {

    @Autowired
    private SnapshotDiffViewer snapshotDiff;

    @Subscribe("projectsTable")
    public void onProjectsTableSelection(Table.SelectionEvent<Project> event) {
        if (!CollectionUtils.isEmpty(event.getSelected())) {
            snapshotDiff.loadVersions(event.getSelected().iterator().next());
        }
    }



}