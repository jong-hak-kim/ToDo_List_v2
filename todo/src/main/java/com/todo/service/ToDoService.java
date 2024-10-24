package com.todo.service;

import com.todo.domain.ToDo;
import com.todo.repository.ToDoRepository;
import com.todo.request.ToDoCreate;
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

        ToDo toDo = new ToDo(toDoCreate.getTitle(), toDoCreate.getContent());
        toDoRepository.save(toDo);
    }
}
