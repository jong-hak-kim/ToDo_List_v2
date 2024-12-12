package com.todo.exception;

public class TodoNotFound extends TodoException {

    private static final String MESSAGE = "존재하지 않는 글입니다.";

    public TodoNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
