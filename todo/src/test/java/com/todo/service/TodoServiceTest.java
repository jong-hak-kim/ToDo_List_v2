package com.todo.service;

import com.todo.domain.Todo;
import com.todo.domain.User;
import com.todo.exception.TodoNotFound;
import com.todo.repository.todo.TodoRepository;
import com.todo.repository.UserRepository;
import com.todo.request.todo.TodoCreate;
import com.todo.request.todo.TodoEdit;
import com.todo.request.todo.TodoSearch;
import com.todo.response.TodoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoService toDoService;

    @Autowired
    private TodoRepository toDoRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void clean() {
        toDoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() throws Exception {
        //given
        User user = User.builder()
                .name("종학")
                .email("whdgkr9070@naver.com")
                .password("1234")
                .build();

        userRepository.save(user);


        TodoCreate toDoCreate = TodoCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //when
        toDoService.write(user.getId(), toDoCreate);

        //then
        assertEquals(1L, toDoRepository.count());
        Todo toDo = toDoRepository.findAll().get(0);
        assertEquals("제목입니다.", toDo.getTitle());
        assertEquals("내용입니다.", toDo.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() throws Exception {
        //given
        Todo requestTodo = Todo.builder()
                .title("foo")
                .content("bar")
                .build();
        toDoRepository.save(requestTodo);

        //when
        TodoResponse response = toDoService.get(requestTodo.getId());

        //then
        assertNotNull(response);
        assertEquals(1L, toDoRepository.count());
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3() throws Exception {
        //given
        List<Todo> requestTodos = IntStream.range(0, 20)
                .mapToObj(i -> Todo.builder()
                        .title("제목 " + i)
                        .content("반포자이 " + i)
                        .build())
                .collect(Collectors.toList());
        toDoRepository.saveAll(requestTodos);

        TodoSearch toDoSearch = TodoSearch.builder()
                .page(1)
                .build();

        //when
        List<TodoResponse> toDos = toDoService.getList(toDoSearch);

        //then
        assertEquals(10L, toDos.size());
        assertEquals("제목 19", toDos.get(0).getTitle());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() throws Exception {
        //given
        Todo toDo = Todo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        toDoRepository.save(toDo);

        TodoEdit toDoEdit = TodoEdit.builder()
                .title("제목")
                .content("내용입니다.")
                .build();

        //when
        toDoService.edit(toDo.getId(), toDoEdit);

        //then
        Todo changeTodo = toDoRepository.findById(toDo.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + toDo.getId()));

        assertEquals("제목", changeTodo.getTitle());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test5() throws Exception {
        //given
        Todo toDo = Todo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        toDoRepository.save(toDo);

        TodoEdit toDoEdit = TodoEdit.builder()
                .title(null)
                .content("내용")
                .build();

        //when
        toDoService.edit(toDo.getId(), toDoEdit);

        //then
        Todo changeTodo = toDoRepository.findById(toDo.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + toDo.getId()));

        assertEquals("내용", changeTodo.getContent());
    }

    @Test
    @DisplayName("글 내용 수정")
    void test6() throws Exception {
        //given
        Todo toDo = Todo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        toDoRepository.save(toDo);

        TodoEdit toDoEdit = TodoEdit.builder()
                .title(null)
                .content("내용")
                .build();

        //when
        toDoService.edit(toDo.getId(), toDoEdit);

        //then
        Todo changeTodo = toDoRepository.findById(toDo.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + toDo.getId()));

        assertEquals("제목입니다.", changeTodo.getTitle());
        assertEquals("내용", changeTodo.getContent());
    }

    @Test
    @DisplayName("게시글 삭제")
    void test7() throws Exception {

        //given
        Todo toDo = Todo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        toDoRepository.save(toDo);

        //when
        toDoService.delete(toDo.getId());

        //then
        assertEquals(0, toDoRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회 - 존재하지 않는 글")
    void test8() throws Exception {
        //given
        Todo toDo = Todo.builder()
                .title("제목")
                .content("반포자이")
                .build();
        toDoRepository.save(toDo);

        //expected
        //예외처리 클래스를 따로 만들어주면
        // 오류가 명확해진다.
        assertThrows(TodoNotFound.class, () -> {
            toDoService.get(toDo.getId() + 1L);
        });

    }

    @Test
    @DisplayName("게시글 삭제 - 존재하지 않는 글")
    void test9() throws Exception {

        //given
        Todo toDo = Todo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        toDoRepository.save(toDo);

        //expected
        assertThrows(TodoNotFound.class, () -> {
            toDoService.delete(toDo.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 내용 수정 - 존재하지 않는 글")
    void test10() throws Exception {
        //given
        Todo toDo = Todo.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();
        toDoRepository.save(toDo);

        TodoEdit toDoEdit = TodoEdit.builder()
                .title(null)
                .content("내용")
                .build();

        //expected
        assertThrows(TodoNotFound.class, () -> {
            toDoService.edit(toDo.getId() + 1L, toDoEdit);
        });

    }


}