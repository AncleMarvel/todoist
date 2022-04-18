package com.example.todo.services;

import com.example.todo.entities.Project;
import com.example.todo.repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project findById(UUID id) {
        return projectRepository.findById(id).orElseThrow();
    }
}
