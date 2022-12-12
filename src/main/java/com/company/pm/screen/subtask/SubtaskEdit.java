package com.company.pm.screen.subtask;

import io.jmix.ui.screen.*;
import com.company.pm.entity.Subtask;

@UiController("Subtask.edit")
@UiDescriptor("subtask-edit.xml")
@EditedEntityContainer("subtaskDc")
@PrimaryEditorScreen(Subtask.class)
public class SubtaskEdit extends StandardEditor<Subtask> {
}