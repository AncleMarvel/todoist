package com.example.todo.controllers;

import com.example.todo.dto.ErrorDto;
import com.example.todo.util.Filters;
import com.example.todo.dto.TodoDto;
import com.example.todo.entities.User;
import com.example.todo.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("todos")
@Validated
public class TodoController {

    private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoDto> getAllDto(
            @RequestParam(name = "isDone", required = false) Boolean isDone,
            @RequestParam(name = "tags", required = false) List<String> tags,
            @RequestParam(name = "projectId", required = false) UUID projectId,
            @RequestParam(name = "priority", required = false) @Max(4) @Min(1) Integer priority,
            @AuthenticationPrincipal User user
    ) {
        var filters = new Filters()
                .setDone(isDone)
                .setTags(tags)
                .setProjectId(projectId)
                .setPriority(priority);

        return todoService.getAll(filters, user);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoDto> getById(@PathVariable UUID todoId, @AuthenticationPrincipal User user) {
        return todoService
                .getTodoById(todoId, user)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public TodoDto createTodo(@Valid @RequestBody TodoDto todoDto, @AuthenticationPrincipal User user) {
        return todoService.createTodoForUser(todoDto, user);
    }

    @PutMapping
    public TodoDto updateTodo(@Valid @RequestBody TodoDto todoDto, @AuthenticationPrincipal User user) {
        return todoService.updateTodoForUser(todoDto, user);
    }

    @DeleteMapping("/{todoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoById(@PathVariable UUID todoId, @AuthenticationPrincipal User user) {
        todoService.deleteTodoById(todoId, user);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> exceptionHandler(Exception e) {
        logger.error("An error occurred: ", e);
        return ResponseEntity
                .internalServerError()
                .body(new ErrorDto("Internal server error"));
    }
}
