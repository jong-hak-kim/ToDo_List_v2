package com.todo.exception;

public class ToDoNotFound extends ToDoException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public ToDoNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
