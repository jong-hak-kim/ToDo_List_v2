package com.todo.repository.todo;

import com.todo.domain.Todo;
import com.todo.request.todo.TodoSearch;
import org.springframework.data.domain.Page;

public interface TodoRepositoryCustom {

    Page<Todo> getList(TodoSearch todoSearch);
}
