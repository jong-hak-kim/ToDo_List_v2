package com.todo.repository.todo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.domain.Todo;
import com.todo.request.todo.TodoSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todo.domain.QTodo.*;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Todo> getList(TodoSearch todoSearch) {
        return jpaQueryFactory.selectFrom(todo)
                .limit(todoSearch.getSize())
                .offset(todoSearch.getOffset())
                .orderBy(todo.id.desc())
                .fetch();
    }
}
