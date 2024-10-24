package com.todo.controller;

import com.todo.domain.ToDo;
import com.todo.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ToDoControllerTest {

    //Mock 테스트
    //웹 애플리케이션 API를 테스트할 때 사용

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

        //expected
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("{}"))
                .andDo(print());
    }

    @Test
    @DisplayName("/todos 요청 시 title값은 필수다.")
    void test2() throws Exception {

        //expected
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다.\"}")
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

        //when
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, toDoRepository.count());

    }

    @Test
    @DisplayName("/todos 요청 시 DB에 값이 저장된다.")
    void test4() throws Exception {

        //when
        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L, toDoRepository.count());

        ToDo toDo = toDoRepository.findAll().get(0);
        assertEquals("제목입니다.", toDo.getTitle());
        assertEquals("내용입니다.", toDo.getContent());

    }

}