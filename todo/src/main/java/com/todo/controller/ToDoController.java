package com.todo.controller;

import com.todo.request.ToDoCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// Http Method
// 각 기능 알기
// GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
// 글 등록
// POST Method

//데이터를 검증하는 이유

// 1. client 개발자가 깜빡할 수 있음 실수로 값을 안 보낼 수 있다.
// 2. client bug로 값이 누락될 수 있다.
// 3. 외부에 누군가 값을 임의로 조작해서 보낼 수 있다.
// 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
// 5. 서버 개발자의 편안함을 위해서

@Slf4j
@RestController
public class ToDoController {



    @PostMapping("/todos")
    public Map<String, String> todos(@RequestBody @Valid ToDoCreate params) {
        //* 아래의 검증 코드 단점
        //1. 매번 메서드마다 값을 검증해야한다.
        //  > 개발자가 까먹을 수 있다.
        //  > 검증 부분에서 버그가 발생할 여지가 높다
        //  > 지겹다
        // 2. 응답값에 HashMap -> 응답 클래스를 만들어주는게 좋다
        // 3. 여러 개의 에러 처리 힘듦
        // 4. 세 번 이상의 반복적인 작업은 피해야 한다.
            // - 코드 && 개발에 관한 모든 것
                // - 자동화 고려
//
//        if (result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField(); //title
//            String errorMessage = firstFieldError.getDefaultMessage();//..에러 메시지
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//            return error;
//        }

        return Map.of();
    }

}
