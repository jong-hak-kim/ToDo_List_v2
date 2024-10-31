package com.todo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Builder
    public ToDo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ToDoEditor.ToDoEditorBuilder toEditor() {
        return ToDoEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(ToDoEditor toDoEditor) {
        title = toDoEditor.getTitle();
        content = toDoEditor.getContent();
    }
}
