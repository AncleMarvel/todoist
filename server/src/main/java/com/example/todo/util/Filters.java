package com.example.todo.util;

import com.example.todo.entities.Tag;
import com.example.todo.entities.Todo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Filters {

    Boolean isDone;
    List<String> tags;
    UUID projectId;
    Integer priority;

    public boolean checkIsFiltersApplied(Todo todo) {
        return checkIsDone(todo) &&
                checkTags(todo) &&
                checkPriority(todo) &&
                checkProjectId(todo);
    }

    public Filters setDone(Boolean done) {
        isDone = done;
        return this;
    }

    public Filters setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public Filters setProjectId(UUID projectId) {
        this.projectId = projectId;
        return this;
    }

    public Filters setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    private boolean checkIsDone(Todo todo) {
        return isDone == null || todo.isDone() == isDone;
    }

    private boolean checkTags(Todo todo) {
        return isDone == null ||
                todo
                        .getTags()
                        .stream()
                        .map(Tag::getName)
                        .collect(Collectors.toSet())
                        .containsAll(tags);
    }

    private boolean checkProjectId(Todo todo) {
        return projectId == null || todo.getProject().getId() == projectId;
    }

    private boolean checkPriority(Todo todo) {
        return priority == null || todo.getPriority() == priority;
    }
}
