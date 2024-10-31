package com.todo.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ToDoEditor {

    private final String title;
    private final String content;

    @Builder
    public ToDoEditor(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
