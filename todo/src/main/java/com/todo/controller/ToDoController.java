package com.todo.controller;

import com.todo.request.ToDoCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class ToDoController {

    // Http Method
    // 각 기능 알기
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method

    @PostMapping("/todos")
    public String todos(@RequestBody ToDoCreate params) {
        log.info("params={}", params.toString());
        return "Hello World";
    }

}
