package com.todo.service;

import com.todo.domain.Comment;
import com.todo.domain.Todo;
import com.todo.exception.TodoNotFound;
import com.todo.repository.comment.CommentRepository;
import com.todo.repository.todo.TodoRepository;
import com.todo.request.comment.CommentCreate;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void write(Long todoId, @Valid CommentCreate request) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(TodoNotFound::new);

        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        Comment comment = Comment.builder()
                .todo(todo)
                .author(request.getAuthor())
                .password(encryptedPassword)
                .content(request.getContent())
                .build();

        todo.addComment(comment);
    }
}
