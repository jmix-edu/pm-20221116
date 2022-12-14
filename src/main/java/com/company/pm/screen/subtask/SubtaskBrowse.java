package com.company.pm.screen.subtask;

import io.jmix.core.Metadata;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.core.querycondition.PropertyConditionUtils;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Filter;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.PropertyFilter;
import io.jmix.ui.component.filter.builder.PropertyConditionBuilder;
import io.jmix.ui.component.filter.configuration.DesignTimeConfiguration;
import io.jmix.ui.component.propertyfilter.SingleFilterSupport;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.pm.entity.Subtask;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Subtask.browse")
@UiDescriptor("subtask-browse.xml")
@LookupComponent("subtasksTable")
public class SubtaskBrowse extends StandardLookup<Subtask> {
    @Autowired
    private CollectionLoader<Subtask> subtasksDl;
    @Autowired
    private Filter filter;
    @Autowired
    private MetadataTools metadataTools;
    @Autowired
    private Metadata metadata;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    protected SingleFilterSupport singleFilterSupport;

    @Subscribe
    public void onAfterInit(AfterInitEvent event) {
        ScreenOptions options = event.getOptions();
        if (options instanceof SubtaskScreenOptions screenOptions) {
            MetaClass aClass = metadata.findClass(Subtask.class);
            Integer taskId = screenOptions.getTaskId();
            MetaPropertyPath metaPropertyPath = metadataTools.resolveMetaPropertyPath(aClass, "task.id");

            filter.setExpanded(true);
            filter.loadConfigurationsAndApplyDefault();

            DesignTimeConfiguration javaDefaultConfiguration =
                    filter.addConfiguration("javaDefaultConfiguration",
                            "Filter By Task ID");

            PropertyFilter propertyFilter = uiComponents.create(PropertyFilter.NAME);
            propertyFilter.setConditionModificationDelegated(true);
            propertyFilter.setDataLoader(filter.getDataLoader());
            propertyFilter.setProperty(metaPropertyPath.toPathString());
            propertyFilter.setOperation(PropertyFilter.Operation.EQUAL);
            propertyFilter.setOperationEditable(true);
            propertyFilter.setParameterName(PropertyConditionUtils.generateParameterName(propertyFilter.getProperty()));
            HasValue valueComponent = singleFilterSupport.generateValueComponent(
                    aClass, propertyFilter.getProperty(), propertyFilter.getOperation()
            );
            propertyFilter.setValueComponent(valueComponent);
            valueComponent.setValue(taskId);

            javaDefaultConfiguration.getRootLogicalFilterComponent().add(propertyFilter);
            filter.setCurrentConfiguration(javaDefaultConfiguration);
        }

    }


    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        filter.apply();
    }





}