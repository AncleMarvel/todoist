package com.example.todo.controllers;

import com.example.todo.dto.ProjectDto;
import com.example.todo.entities.Project;
import com.example.todo.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @GetMapping
    public List<Project> getAll(@AuthenticationPrincipal User user) {
        return null;
    }

    @GetMapping("/{projectId}")
    public Project getById(@PathVariable UUID projectId, User user) {
        return null;
    }

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectDto projectDto) {
        return null;
    }

    @PutMapping
    public ProjectDto updateProject(@RequestBody ProjectDto projectDto) {
        return null;
    }

    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProject(@PathVariable UUID projectId) {

    }

}
