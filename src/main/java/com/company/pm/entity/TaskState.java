package com.company.pm.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum TaskState implements EnumClass<String> {

    NEW("N"),
    STARTED("S"),
    FINISHED("F");

    private String id;

    TaskState(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static TaskState fromId(String id) {
        for (TaskState at : TaskState.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}