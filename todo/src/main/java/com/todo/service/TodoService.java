package com.todo.service;

import com.todo.domain.Todo;
import com.todo.domain.TodoEditor;
import com.todo.domain.User;
import com.todo.exception.TodoNotFound;
import com.todo.exception.UserNotFound;
import com.todo.repository.UserRepository;
import com.todo.repository.todo.TodoRepository;
import com.todo.request.todo.TodoCreate;
import com.todo.request.todo.TodoEdit;
import com.todo.request.todo.TodoSearch;
import com.todo.response.PagingResponse;
import com.todo.response.TodoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    public void write(Long userId, TodoCreate todoCreate) {
        // toDocreate -> Entity로 변환시켜줘야 한다.

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        Todo todo = Todo.builder()
                .user(user)
                .title(todoCreate.getTitle())
                .content(todoCreate.getContent())
                .build();

        todoRepository.save(todo);
    }

    public TodoResponse get(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(TodoNotFound::new);

        return new TodoResponse(todo);
    }

    // 글이 너무 많은 경우 -> 비용이 많이 든다.
    // 글이 100,000,000인 경우 DB를 모두 조회하게 되면 DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽 비용 등이 발생할 수 있다.

    public PagingResponse<TodoResponse> getList(TodoSearch todoSearch) {
        Page<Todo> todoPage = todoRepository.getList(todoSearch);
        PagingResponse<TodoResponse> todoList = new PagingResponse<>(todoPage, TodoResponse.class);
        return todoList;
    }

    @Transactional
    public void edit(Long id, TodoEdit todoEdit) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(TodoNotFound::new);

        TodoEditor.TodoEditorBuilder todoEditorBuilder = todo.toEditor();

        TodoEditor todoEditor = todoEditorBuilder.title(todoEdit.getTitle())
                .content(todoEdit.getContent())
                .build();

        todo.edit(todoEditor);

    }

    public void delete(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(TodoNotFound::new);

        todoRepository.delete(todo);
    }
}
