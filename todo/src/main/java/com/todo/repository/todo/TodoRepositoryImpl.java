package com.todo.repository.todo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todo.domain.Todo;
import com.todo.request.todo.TodoSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static com.todo.domain.QTodo.todo;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Page<Todo> getList(TodoSearch todoSearch) {
        long totalCount = jpaQueryFactory.select(todo.count())
                .from(todo)
                .fetchFirst();

        List<Todo> items = jpaQueryFactory.selectFrom(todo)
                .limit(todoSearch.getSize())
                .offset(todoSearch.getOffset())
                .orderBy(todo.id.desc())
                .fetch();

        return new PageImpl<>(items, todoSearch.getPageable(), totalCount);
    }
}
