package com.todo.repository;

import com.todo.domain.ToDo;
import com.todo.request.ToDoSearch;

import java.util.List;

public interface ToDoRepositoryCustom {

    List<ToDo> getList(ToDoSearch toDoSearch);
}
