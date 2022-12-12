package com.company.pm.screen.subtask;

import io.jmix.ui.screen.ScreenOptions;

public class SubtaskScreenOptions implements ScreenOptions {

    private final Integer taskId;

    public SubtaskScreenOptions(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getTaskId() {
        return taskId;
    }
}
