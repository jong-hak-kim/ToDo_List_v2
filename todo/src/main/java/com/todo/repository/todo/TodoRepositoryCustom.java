package com.todo.repository.todo;

import com.todo.domain.Todo;
import com.todo.request.todo.TodoSearch;

import java.util.List;

public interface TodoRepositoryCustom {

    List<Todo> getList(TodoSearch todoSearch);
}
