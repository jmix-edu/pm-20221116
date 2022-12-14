package com.company.pm.datatype;

import io.jmix.core.JmixOrder;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.datatype.Datatype;
import io.jmix.core.metamodel.model.MetaClass;
import io.jmix.core.metamodel.model.MetaPropertyPath;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.ComponentGenerationContext;
import io.jmix.ui.component.ComponentGenerationStrategy;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.data.ValueSource;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component("pm_ProjectLabelsDataTypeComponentGenerationStrategy")
public class ProjectLabelsDataTypeComponentGenerationStrategy implements ComponentGenerationStrategy, Ordered {

    private final MetadataTools metadataTools;
    private final UiComponents uiComponents;

    public ProjectLabelsDataTypeComponentGenerationStrategy(MetadataTools metadataTools, UiComponents uiComponents) {
        this.metadataTools = metadataTools;
        this.uiComponents = uiComponents;
    }

    @Nullable
    @Override
    public io.jmix.ui.component.Component createComponent(ComponentGenerationContext context) {
        MetaClass metaClass = context.getMetaClass();
        MetaPropertyPath metaPropertyPath = metadataTools.resolveMetaPropertyPathOrNull(metaClass, context.getProperty());
        if (metaPropertyPath != null && metaPropertyPath.getRange().isDatatype()
            && ((Datatype<?>)metaPropertyPath.getRange().asDatatype() instanceof ProjectLabelsDataType)) {
            TextField textField = uiComponents.create(TextField.class);
            textField.setInputPrompt("Please enter labels");
            ValueSource valueSource = context.getValueSource();
            if (valueSource != null) {
                textField.setValueSource(valueSource);
            }
            return textField;
        }

        return null;
    }

    @Override
    public int getOrder() {
        return JmixOrder.HIGHEST_PRECEDENCE + 10;
    }
}
