package com.todo.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table (
        indexes = {
                @Index(name = "IDX_COMMENT_TODO_ID", columnList = "todo_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn
    private Todo todo;

    @Builder
    public Comment(String author, String password, String content, Todo todo) {
        this.author = author;
        this.password = password;
        this.content = content;
        this.todo = this.todo;
    }

    public void setTodo(Todo todo){
        this.todo = todo;
    }
}
