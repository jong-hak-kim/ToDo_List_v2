package com.todo.response;

import com.todo.domain.Todo;
import lombok.Getter;

import java.time.LocalDate;

//서비스 정책에 맞는 클래스
@Getter
public class TodoResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final LocalDate regDate;

    // 생성자 오버로딩
    public TodoResponse(Todo toDo) {
        this.id = toDo.getId();
        this.title = toDo.getTitle();
        this.content = toDo.getContent();
        this.regDate = toDo.getRegDate().toLocalDate();
    }

}
