package com.todo.service;

import com.todo.domain.ToDo;
import com.todo.repository.ToDoRepository;
import com.todo.request.ToDoCreate;
import com.todo.response.ToDoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public void write(ToDoCreate toDoCreate) {
        // toDocreate -> Entity로 변환시켜줘야 한다.

        ToDo toDo = ToDo.builder()
                .title(toDoCreate.getTitle())
                .content(toDoCreate.getContent())
                .build();

        toDoRepository.save(toDo);
    }

    public ToDoResponse get(Long id) {
        ToDo toDo = toDoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글 ID입니다."));

        ToDoResponse response = ToDoResponse.builder()
                .id(toDo.getId())
                .title(toDo.getTitle())
                .content(toDo.getContent())
                .build();

        /**
         * 응답 변환을 여기서 굳이 해야 하나?
         * Controller -> WebToDoService -> Repository
         *               ToDoService
         *
         *
         * Response와 같은 행위를 하는 것들은 WebToDoService 여기에 두고
         * 다른 서비스와 통신을 하기 위한 것은 ToDoService에 한다.
         *
         * 위 방식으로 하기에는 물리적 한계가 존재하여
         * 보통은 ToDoService에서 하기도 한다.
         */

        return response;
    }

}
