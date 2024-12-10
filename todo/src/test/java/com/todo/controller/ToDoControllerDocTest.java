package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.domain.ToDo;
import com.todo.repository.ToDoRepository;
import com.todo.request.ToDoCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.todo.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class ToDoControllerDocTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @Test
    @DisplayName("글 단건 조회 테스트")
    void test1() throws Exception {
        //given
        ToDo toDo = ToDo.builder()
                .title("제목")
                .content("내용")
                .build();

        toDoRepository.save(toDo);

        // expected
        this.mockMvc.perform(get("/todos/{toDoId}", 1L)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-inquiry",
                        pathParameters(
                                parameterWithName("toDoId").description("할 일 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").description("할 일 ID"),
                                fieldWithPath("title").description("제목"),
                                fieldWithPath("content").description("내용")
                        )
                ));
    }

    @Test
    @WithMockUser(username = "whdgkr9070@naver.com", roles = {"ADMIN"})
    @DisplayName("글 등록")
    void test2() throws Exception {
        //given
        ToDoCreate request = ToDoCreate.builder()
                .title("할 일")
                .content("반포자이.")
                .build();

        String json = objectMapper.writeValueAsString(request);


        // expected
        this.mockMvc.perform(post("/todos")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        requestFields(
                                PayloadDocumentation.fieldWithPath("title").description("제목")
                                        .attributes(key("constraint").value("좋은 제목 입력해주세요.")),
                                PayloadDocumentation.fieldWithPath("content").description("내용").optional()
                        )
                ));
    }


}
