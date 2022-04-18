package com.example.todo.repositories;

import com.example.todo.entities.Todo;
import com.example.todo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, UUID> {

    List<Todo> findAllByOwner(User owner);

    Optional<Todo> findTodoByIdAndOwner(UUID id, User owner);

    void deleteByIdAndOwner(UUID id, User owner);
}
