package com.todo.exception;

public class ToDoNotFound extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public ToDoNotFound() {
        super(MESSAGE);
    }
}
