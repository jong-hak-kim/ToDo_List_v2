package com.todo.service;

import com.todo.domain.ToDo;
import com.todo.domain.ToDoEditor;
import com.todo.domain.User;
import com.todo.exception.ToDoNotFound;
import com.todo.exception.UserNotFound;
import com.todo.repository.ToDoRepository;
import com.todo.repository.UserRepository;
import com.todo.request.ToDoCreate;
import com.todo.request.ToDoEdit;
import com.todo.request.ToDoSearch;
import com.todo.response.ToDoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final UserRepository userRepository;
    private final ToDoRepository toDoRepository;

    public void write(Long userId, ToDoCreate toDoCreate) {
        // toDocreate -> Entity로 변환시켜줘야 한다.

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);

        ToDo toDo = ToDo.builder()
                .user(user)
                .title(toDoCreate.getTitle())
                .content(toDoCreate.getContent())
                .build();

        toDoRepository.save(toDo);
    }

    public ToDoResponse get(Long id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(ToDoNotFound::new);

        return ToDoResponse.builder()
                .id(toDo.getId())
                .title(toDo.getTitle())
                .content(toDo.getContent())
                .build();
    }

    // 글이 너무 많은 경우 -> 비용이 많이 든다.
    // 글이 100,000,000인 경우 DB를 모두 조회하게 되면 DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽 비용 등이 발생할 수 있다.

    public List<ToDoResponse> getList(ToDoSearch toDoSearch) {
        return toDoRepository.getList(toDoSearch).stream()
                .map(ToDoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, ToDoEdit toDoEdit) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(ToDoNotFound::new);

        ToDoEditor.ToDoEditorBuilder toDoEditorBuilder = toDo.toEditor();

        ToDoEditor toDoEditor = toDoEditorBuilder.title(toDoEdit.getTitle())
                .content(toDoEdit.getContent())
                .build();

        toDo.edit(toDoEditor);

    }

    public void delete(Long id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(ToDoNotFound::new);

        toDoRepository.delete(toDo);
    }
}
