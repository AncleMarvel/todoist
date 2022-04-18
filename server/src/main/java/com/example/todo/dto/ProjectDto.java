package com.example.todo.dto;

import java.util.Set;
import java.util.UUID;

public class ProjectDto {

    private UUID id = null;
    private String name;
    private Set<UUID> todos = null;

    public UUID getId() {
        return id;
    }

    public ProjectDto setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProjectDto setName(String name) {
        this.name = name;
        return this;
    }

    public Set<UUID> getTodos() {
        return todos;
    }

    public ProjectDto setTodos(Set<UUID> todos) {
        this.todos = todos;
        return this;
    }
}
