package com.todo.service;

import com.todo.domain.ToDo;
import com.todo.repository.ToDoRepository;
import com.todo.request.ToDoCreate;
import com.todo.response.ToDoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ToDoServiceTest {

    @Autowired
    private ToDoService toDoService;

    @Autowired
    private ToDoRepository toDoRepository;

    @BeforeEach
    void clean() {
        toDoRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() throws Exception {
        //given
        ToDoCreate toDoCreate = ToDoCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        toDoService.write(toDoCreate);

        //then
        assertEquals(1L, toDoRepository.count());
        ToDo toDo = toDoRepository.findAll().get(0);
        assertEquals("제목입니다.", toDo.getTitle());
        assertEquals("내용입니다.", toDo.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() throws Exception {
        //given
        ToDo requestToDo = ToDo.builder()
                .title("foo")
                .content("bar")
                .build();
        toDoRepository.save(requestToDo);

        //when
        ToDoResponse response = toDoService.get(requestToDo.getId());

        //then
        assertNotNull(response);
        assertEquals(1L, toDoRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

}