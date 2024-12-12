package com.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.domain.Comment;
import com.todo.domain.Todo;
import com.todo.domain.User;
import com.todo.repository.UserRepository;
import com.todo.repository.comment.CommentRepository;
import com.todo.repository.todo.TodoRepository;
import com.todo.request.comment.CommentCreate;
import com.todo.request.comment.CommentDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clean() {
        toDoRepository.deleteAll();
        userRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 작성")
    void test1() throws Exception {
        //given
        User user = User.builder()
                .name("종학")
                .email("whdgkr9070@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        Todo toDo = Todo.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        toDoRepository.save(toDo);

        CommentCreate request = CommentCreate.builder()
                .author("종학이")
                .password("123456")
                .content("댓글입니다. dddddd 10글자 제한")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/todos/{todoId}/comments", toDo.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(1L, commentRepository.count());

        Comment savedComment = commentRepository.findAll().get(0);
        assertEquals("종학이", savedComment.getAuthor());
        assertNotEquals("123456", savedComment.getPassword());
        assertTrue(passwordEncoder.matches("123456", savedComment.getPassword()));
        assertEquals("댓글입니다. dddddd 10글자 제한", savedComment.getContent());
    }

    @Test
    @DisplayName("댓글 삭제")
    void test2() throws Exception {
        //given
        User user = User.builder()
                .name("종학")
                .email("whdgkr9070@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);

        Todo toDo = Todo.builder()
                .title("123456789012345")
                .content("bar")
                .user(user)
                .build();

        toDoRepository.save(toDo);

        String encryptedPassword = passwordEncoder.encode("123456");

        Comment comment = Comment.builder()
                .author("종학이")
                .password(encryptedPassword)
                .content("dfsdfsdfdfd 10글자 제한")
                .build();

        toDo.addComment(comment);
        comment.setTodo(toDo);
        commentRepository.save(comment);

        CommentDelete request = new CommentDelete("123456");
        String json = objectMapper.writeValueAsString(request);

        //expected
        mockMvc.perform(post("/comments/{commentId}/delete", comment.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

}