package com.todo.controller;

import com.todo.request.comment.CommentCreate;
import com.todo.request.comment.CommentDelete;
import com.todo.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todos/{todoId}/comments")
    public void write(@PathVariable Long todoId, @RequestBody @Valid CommentCreate request) {
        commentService.write(todoId, request);
    }

    @PostMapping("/comments/{commentId}/delete")
    public void delete(@PathVariable Long commentId, @RequestBody @Valid CommentDelete request){
        commentService.delete(commentId, request);
    }
}
