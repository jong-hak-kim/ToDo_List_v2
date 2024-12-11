package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.config.ToDoMockUser;
import com.todo.domain.ToDo;
import com.todo.domain.User;
import com.todo.repository.ToDoRepository;
import com.todo.repository.UserRepository;
import com.todo.request.ToDoCreate;
import com.todo.request.ToDoEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//jsonPath 검증 방법 공부

// 글 제목
// 글 내용
// 사용자
// id
// user
// level

//key Value 데이터의 한계
//title=xx&content=xx&userId=xxx&userName=xxx&userLevel=xxx
//모든것을 풀어서 나열해야한다.

//대신에 json 사용

/*
 * {
 *   "title": "xxx",
 *   "content": "xxx",
 *   "user": {
 *           "id": "xxx",
 *           "name": "xxx"
 *       }
 * }
 */

@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

    //Mock 테스트
    //웹 애플리케이션 API를 테스트할 때 사용

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean() {
        toDoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @DisplayName("글 작성 요청 시 title값은 필수다.")
    void test2() throws Exception {

        //given
        ToDoCreate request = ToDoCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        //expected
        mockMvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력하세요."))
                .andDo(print());
    }

    @Test
//    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @ToDoMockUser
    @DisplayName("글 작성 요청 시 DB에 값이 저장된다.")
    void test3() throws Exception {

        //given
        ToDoCreate request = ToDoCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, toDoRepository.count());

    }

    @Test
//    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @ToDoMockUser
    @DisplayName("글 작성")
    void test4() throws Exception {

        //given
        ToDoCreate request = ToDoCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        mockMvc.perform(post("/todos")
                        .header("authorization", "todo")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, toDoRepository.count());

        ToDo toDo = toDoRepository.findAll().get(0);
        assertEquals("제목입니다.", toDo.getTitle());
        assertEquals("내용입니다.", toDo.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test5() throws Exception {
        //given
        User user = User.builder()
                .name("종학")
                .email("whdgkr9070@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        ToDo toDo = ToDo.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        toDoRepository.save(toDo);


        //클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자로 해주세요.

        //expected
        mockMvc.perform(get("/todos/{toDoId}", toDo.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(toDo.getId()))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 여러 개 조회")
    void test6() throws Exception {
        //given
        User user = User.builder()
                .name("종학")
                .email("whdgkr9070@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        List<ToDo> requestToDos = IntStream.range(0, 20)
                .mapToObj(i -> ToDo.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());
        toDoRepository.saveAll(requestToDos);

        //클라이언트 요구사항
        // json 응답에서 title 값 길이를 최대 10글자로 해주세요.

        //expected
        /**
         * List로 표현됨
         * [{id:..., title:...}, {id:..., title:...}]
         */
        mockMvc.perform(get("/todos?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test7() throws Exception {
        //given
        User user = User.builder()
                .name("종학")
                .email("whdgkr9070@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        List<ToDo> requestToDos = IntStream.range(0, 20)
                .mapToObj(i -> ToDo.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
                        .user(user)
                        .build())
                .collect(Collectors.toList());
        toDoRepository.saveAll(requestToDos);

        mockMvc.perform(get("/todos?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());

    }

    @Test
    @ToDoMockUser
//    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @DisplayName("글 제목 수정")
    void test8() throws Exception {
        //given
        User user = userRepository.findAll().get(0);

        ToDo toDo = ToDo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();
        toDoRepository.save(toDo);

        ToDoEdit toDoEdit = ToDoEdit.builder()
                .title("제목")
                .content("내용입니다.")
                .build();

        mockMvc.perform(patch("/todos/{toDoId}", toDo.getId())

                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoEdit)))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
//    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @ToDoMockUser
    @DisplayName("게시글 삭제")
    void test9() throws Exception {
        //given
        User user = userRepository.findAll().get(0);

        ToDo toDo = ToDo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .user(user)
                .build();
        toDoRepository.save(toDo);

        // expected
        mockMvc.perform(delete("/todos/{toDoId}", toDo.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
//    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @ToDoMockUser
    @DisplayName("존재하지 않는 게시글 조회")
    void test10() throws Exception {
        //expected
        mockMvc.perform(get("/todos/{toDoId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
//    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @ToDoMockUser
    @DisplayName("존재하지 않는 게시글 수정")
    void test11() throws Exception {

        ToDoEdit toDoEdit = ToDoEdit.builder()
                .title("제목")
                .content("내용입니다.")
                .build();

        //expected
        mockMvc.perform(patch("/todos/{toDoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // Spring RestDocs
    // - 운영코드에 영향을 주지 않는다
    // - 코드 수정 -> 문서를 수정하지 않으면
    // 코드(기능) < - > 문서와 달라서 신뢰성이 떨어진다
    // - Test 케이스를 실행한 후 문서를 자동으로 생성해준다.
    // -> 신뢰성이 높아진다

}