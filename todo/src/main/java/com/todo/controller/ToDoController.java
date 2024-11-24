package com.todo.controller;

import com.todo.config.data.UserSession;
import com.todo.request.ToDoCreate;
import com.todo.request.ToDoEdit;
import com.todo.request.ToDoSearch;
import com.todo.response.ToDoResponse;
import com.todo.service.ToDoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

// POST 요청은 대부분 200, 201 응답코드 사용
// 응답 케이스 예시
// Case1. 저장한 데이터 Entity를 Response로 응답
// Case2. 저장한 데이터의 primary_id -> Response로 응답
//   - Client에서는 수신한 id를 글 조회 API를 통해서 글 데이터를 수신받는다
// Case3. 응답 필요 없음 -> 클라이언트에서 모든 TODO(할 일) 데이터 context를 잘 관리함
// Bad Case: 서버에서 -> 반드시 이렇게 할 겁니다
//  -> 서버에서 차라리 유용하게 대응하는 것이 좋다(대신 코드 잘 짜야 함)
//  -> 한 번에 일괄적으로 잘 처리되는 케이스가 없기 때문에 잘 관리하는 형태가 중요


// 빌더의 장점
// - 가독성에 좋다 (값 생성에 대한 유연함)
// - 필요한 값만 받을 수 있다.
// - 객체의 불변성

@Slf4j
@RestController
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping("/foo")
    public String foo(UserSession userSession) {
        log.info(">>>{}", userSession.name);
        return userSession.name;
    }

    @GetMapping("/bar")
    public String bar(UserSession userSession) {
        return "인증이 필요한 페이지";
    }


    @PostMapping("/todos")
    public void todos(@RequestBody @Valid ToDoCreate request) {
        // 인증 방법
        //1. GET Parameter
        //2. POST(body) value (하지만 POST 바디 로 받게 되면
        // 현재 받고 있는 값과 인증 값을 같이 받아야하기 때문에 좋은 방법이 아니다)
        //3. Header
        request.validate();
        toDoService.write(request);
    }

    /**
     * 조회 API
     * /todos -> 글 전체 조회(검색 + 페이징)
     * /todos/{toDoId} -> 글 한 개 조회
     */

    @GetMapping("/todos/{toDoId}")
    public ToDoResponse get(@PathVariable Long toDoId) {
        //응답 클래스 분리(서비스 정책에 맞는)
        //Request 클래스
        //Response 클래스 명확하게 분리
        return toDoService.get(toDoId);
    }

    @GetMapping("/todos")
    public List<ToDoResponse> getList(@ModelAttribute ToDoSearch toDoSearch) {
        return toDoService.getList(toDoSearch);
    }

    @PatchMapping("/todos/{toDoId}")
    public void edit(@PathVariable Long toDoId, @RequestBody @Valid ToDoEdit request) {
        toDoService.edit(toDoId, request);
    }

    @DeleteMapping("/todos/{toDoId}")
    public void delete(@PathVariable Long toDoId) {
        toDoService.delete(toDoId);
    }


}
