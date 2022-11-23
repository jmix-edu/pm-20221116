package com.company.pm.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ProjectLabels implements Serializable {

    private List<String> labels;

    public ProjectLabels() {
    }

    public ProjectLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public boolean add(String s) {
        return labels.add(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectLabels that = (ProjectLabels) o;
        return Objects.equals(labels, that.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    @Override
    public String toString() {
        return "{" + labels + '}';
    }
}
