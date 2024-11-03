package com.todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.domain.ToDo;
import com.todo.request.ToDoSearch;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.todo.domain.QToDo.*;

@RequiredArgsConstructor
public class ToDoRepositoryImpl implements ToDoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ToDo> getList(ToDoSearch toDoSearch) {
        return jpaQueryFactory.selectFrom(toDo)
                .limit(toDoSearch.getSize())
                .offset(toDoSearch.getOffset())
                .orderBy(toDo.id.desc())
                .fetch();
    }
}
