package com.example.todo.util;

import com.example.todo.dto.TodoDto;
import com.example.todo.entities.Tag;
import com.example.todo.entities.Todo;
import com.example.todo.services.ProjectService;
import com.example.todo.services.TagService;
import com.example.todo.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TodoMapper extends Mapper<Todo, TodoDto> {

    private final TagService tagService;
    private final ProjectService projectService;
    private final UserService userService;

    private final TypeMap<Todo, TodoDto> toDtoMapper;
    private final TypeMap<TodoDto, Todo> toEntityMapper;

    public TodoMapper(
            TagService tagService,
            ProjectService projectService,
            UserService userService,
            ModelMapper modelMapper
    ) {
        this.tagService = tagService;
        this.projectService = projectService;
        this.userService = userService;

        toDtoMapper = configureToDtoMapper(modelMapper);
        toEntityMapper = configureToEntityMapper(modelMapper);
    }

    @Override
    protected TypeMap<Todo, TodoDto> getToDtoMapper() {
        return toDtoMapper;
    }

    @Override
    protected TypeMap<TodoDto, Todo> getToEntityMapper() {
        return toEntityMapper;
    }

    private TypeMap<Todo, TodoDto> configureToDtoMapper(ModelMapper modelMapper) {
        return modelMapper
                .createTypeMap(Todo.class, TodoDto.class)
                .addMapping(
                        src -> Optional.ofNullable(src.getTags())
                                .map(tags -> tags.stream().map(Tag::getName).collect(Collectors.toSet()))
                                .orElse(new HashSet<>()),
                        TodoDto::setTags
                )
                .addMapping(src -> src.getProject().getId(), TodoDto::setProjectId)
                .addMapping(src -> src.getOwner().getId(), TodoDto::setOwnerId)
                .addMapping(src -> src.getExecutor().getId(), TodoDto::setExecutorId);
    }

    private TypeMap<TodoDto, Todo> configureToEntityMapper(ModelMapper modelMapper) {
        return modelMapper
                .createTypeMap(TodoDto.class, Todo.class)
                .addMapping(
                        src -> Optional.ofNullable(src.getTags())
                                .map(tags -> tags.stream().map(tagService::getOrCreateByName).collect(Collectors.toSet()))
                                .orElse(new HashSet<>()),
                        Todo::setTags)
                .addMapping(src -> Optional.ofNullable(src.getProjectId()).map(projectService::findById).orElse(null), Todo::setProject)
                .addMapping(src -> Optional.ofNullable(src.getOwnerId()).map(userService::findById).orElse(null), Todo::setOwner)
                .addMapping(src -> Optional.ofNullable(src.getExecutorId()).map(userService::findById).orElse(null), Todo::setExecutor);

    }

}
