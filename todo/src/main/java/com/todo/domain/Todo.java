package com.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Column(nullable = false)
    private LocalDateTime regDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "todo")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Todo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.regDate = LocalDateTime.now();
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
