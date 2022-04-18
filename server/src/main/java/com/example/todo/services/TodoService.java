package com.example.todo.services;

import com.example.todo.util.Filters;
import com.example.todo.dto.TodoDto;
import com.example.todo.entities.Todo;
import com.example.todo.entities.User;
import com.example.todo.repositories.TodoRepository;
import com.example.todo.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;
    private final Mapper<Todo, TodoDto> todoMapper;

    public TodoService(TodoRepository todoRepository, Mapper<Todo, TodoDto> todoMapper) {
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
    }

    @Transactional
    public TodoDto createTodoForUser(TodoDto todoDto, User user) {
        var todo = todoMapper.toEntity(todoDto);
        todo.setId(null);
        todo.setOwner(user);
        todo.setCreatedDate(LocalDateTime.now());
        return todoMapper.toDto(todoRepository.save(todo));
    }


    public List<TodoDto> getAll(Filters filters, User user) {
        return todoRepository
                .findAllByOwner(user)
                .stream()
                .filter(filters::checkIsFiltersApplied)
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TodoDto> getTodoById(UUID todoId, User user) {
        return todoRepository
                .findTodoByIdAndOwner(todoId, user)
                .map(todoMapper::toDto);
    }

    @Transactional
    public void deleteTodoById(UUID todoId, User user) {
        try {
            todoRepository.deleteByIdAndOwner(todoId, user);
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public TodoDto updateTodoForUser(TodoDto todoDto, User user) {
        var todo = todoMapper.toEntity(todoDto);
        todo.setOwner(user);
        return todoMapper.toDto(todoRepository.save(todo));
    }
}
