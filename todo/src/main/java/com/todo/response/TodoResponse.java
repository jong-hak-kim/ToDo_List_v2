package com.todo.response;

import com.todo.domain.Todo;
import lombok.Builder;
import lombok.Getter;

//서비스 정책에 맞는 클래스
@Getter
public class TodoResponse {

    private final Long id;
    private final String title;
    private final String content;

    // 생성자 오버로딩
    public TodoResponse(Todo toDo) {
        this.id = toDo.getId();
        this.title = toDo.getTitle();
        this.content = toDo.getContent();
    }

    @Builder
    public TodoResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0, Math.min(title.length(), 10));
        this.content = content;
    }

}
