package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.domain.ToDo;
import com.todo.repository.ToDoRepository;
import com.todo.request.ToDoCreate;
import com.todo.request.ToDoEdit;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @BeforeEach
    void clean() {
        toDoRepository.deleteAll();
    }

    @Test
    @DisplayName("/todos 요청 시 Hello World를 출력한다.")
    void test() throws Exception {

        ToDoCreate request = ToDoCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andExpect(content().string(""))
                .andDo(print());
    }

    @Test
    @DisplayName("/todos 요청 시 title값은 필수다.")
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
    @DisplayName("/todos 요청 시 DB에 값이 저장된다.")
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
    @DisplayName("/todos 요청 시 DB에 값이 저장된다.")
    void test4() throws Exception {

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

        ToDo toDo = toDoRepository.findAll().get(0);
        assertEquals("제목입니다.", toDo.getTitle());
        assertEquals("내용입니다.", toDo.getContent());

    }

    @Test
    @DisplayName("글 1개 조회")
    void test5() throws Exception {
        //given
        ToDo toDo = ToDo.builder()
                .title("123456789012345")
                .content("bar")
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
        List<ToDo> requestToDos = IntStream.range(0, 20)
                .mapToObj(i -> ToDo.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
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
                .andExpect(jsonPath("$.length()",is(10)))
                .andExpect(jsonPath("$[0].title").value("제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());

    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test7() throws Exception {
        //given
        List<ToDo> requestToDos = IntStream.range(0, 20)
                .mapToObj(i -> ToDo.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        toDoRepository.saveAll(requestToDos);

        mockMvc.perform(get("/todos?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()",is(10)))
                .andExpect(jsonPath("$[0].title").value("제목 19"))
                .andExpect(jsonPath("$[0].content").value("반포자이 19"))
                .andDo(print());

    }

    @Test
    @DisplayName("글 제목 수정")
    void test8() throws Exception {
        //given
        ToDo toDo = ToDo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
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

}