package com.company.pm.datatype;

import com.company.pm.entity.ProjectLabels;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import io.jmix.core.metamodel.annotation.DatatypeDef;
import io.jmix.core.metamodel.annotation.Ddl;
import io.jmix.core.metamodel.datatype.Datatype;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

@DatatypeDef(id = "projectLabels", javaClass = ProjectLabels.class, defaultForClass = true)
@Ddl(value = "varchar(500)")
public class ProjectLabelsDataType implements Datatype<ProjectLabels> {

    @Override
    public String format(@Nullable Object value) {
        if (value instanceof ProjectLabels projectLabels) {
            return Joiner.on(", ").join(projectLabels.getLabels());
        }
        return null;
    }

    @Override
    public String format(@Nullable Object value, Locale locale) {
        return format(value);
    }

    @Nullable
    @Override
    public ProjectLabels parse(@Nullable String value) throws ParseException {
        List<String> strings = Splitter.on(",").trimResults().splitToList(value);
        return new ProjectLabels(strings);
    }

    @Nullable
    @Override
    public ProjectLabels parse(@Nullable String value, Locale locale) throws ParseException {
        return parse(value);
    }
}
