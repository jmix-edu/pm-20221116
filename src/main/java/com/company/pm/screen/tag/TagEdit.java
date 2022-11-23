package com.company.pm.screen.tag;

import io.jmix.ui.screen.*;
import com.company.pm.entity.Tag;

@UiController("Tag.edit")
@UiDescriptor("tag-edit.xml")
@EditedEntityContainer("tagDc")
public class TagEdit extends StandardEditor<Tag> {
}