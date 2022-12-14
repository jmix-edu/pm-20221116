package com.company.pm.screen.programmatic;

import com.company.pm.entity.Project;
import io.jmix.core.FetchPlan;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.ui.Facets;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.DataLoadCoordinator;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.component.Table;
import io.jmix.ui.component.data.table.ContainerGroupTableItems;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataComponents;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

//@PrimaryLookupScreen(Project.class)
@DialogMode(width = "800px", height = "600px")
@UiController("ProgrammaticProjectBrowser")
@LookupComponent("projectsTable")
public class ProgrammaticBrowser extends StandardLookup<Project> {

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private DataComponents dataComponents;
    @Autowired
    private Facets facets;
    @Autowired
    private MetadataTools metadataTools;

    private CollectionContainer<Project> projectsDc;
    private CollectionLoader<Project> projectsDl;
    private GroupTable<Project> projectsTable;


    @Subscribe
    public void onInit(InitEvent event) {
        DataContext dataContext = dataComponents.createDataContext();
        getScreenData().setDataContext(dataContext);
        projectsDc = dataComponents.createCollectionContainer(Project.class);
        projectsDl = dataComponents.createCollectionLoader();

        getScreenData().registerContainer("projectDc", projectsDc);
        projectsDl.setContainer(projectsDc);

        projectsDl.setQuery("select p from Project p");
        projectsDl.setFetchPlan(FetchPlan.BASE);

        getScreenData().registerLoader("projectDl", projectsDl);

        DataLoadCoordinator coordinator = facets.create(DataLoadCoordinator.class);
        getWindow().addFacet(coordinator);
        coordinator.configureAutomatically();

        projectsTable = uiComponents.create(GroupTable.of(Project.class));
        projectsTable.setId("projectsTable");
        projectsTable.setWidthFull();

        addColumn("name");
        addColumn("startDate");
        addColumn("endDate");

        projectsTable.setItems(new ContainerGroupTableItems<>(projectsDc));

        getWindow().add(projectsTable);
    }

    private void addColumn(String colId) {
        MetaClass metaClass = projectsDc.getEntityMetaClass();
        MetaPropertyPath metaPropertyPath = metadataTools.resolveMetaPropertyPath(metaClass, colId);
        Table.Column<Project> column = projectsTable.addColumn(metaPropertyPath);
        column.setCaption(colId);
    }


}
