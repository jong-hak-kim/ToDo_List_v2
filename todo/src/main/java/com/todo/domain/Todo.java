package com.todo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "todo")
    private List<Comment> comments;

    @Builder
    public Todo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public TodoEditor.TodoEditorBuilder toEditor() {
        return TodoEditor.builder()
                .title(title)
                .content(content);
    }

    public void edit(TodoEditor toDoEditor) {
        title = toDoEditor.getTitle();
        content = toDoEditor.getContent();
    }

    public Long getUserId() {
        return this.user.getId();
    }

    public void addComment(Comment comment) {
        comment.setTodo(this);
        this.comments.add(comment);
    }
}
