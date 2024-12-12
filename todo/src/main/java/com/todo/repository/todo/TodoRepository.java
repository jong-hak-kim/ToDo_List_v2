package com.todo.repository.todo;

import com.todo.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> , TodoRepositoryCustom {

}
