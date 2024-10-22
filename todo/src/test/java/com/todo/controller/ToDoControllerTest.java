package com.todo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

}