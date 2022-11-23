package com.company.pm.screen.tag;

import io.jmix.ui.screen.*;
import com.company.pm.entity.Tag;

@UiController("Tag.browse")
@UiDescriptor("tag-browse.xml")
@LookupComponent("tagsTable")
public class TagBrowse extends StandardLookup<Tag> {
}