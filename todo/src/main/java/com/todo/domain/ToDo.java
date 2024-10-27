package com.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public String getTitle() {
        //Entity에서 getter를 만들때는 서비스의 정책을 넣으면 안된다! 절대!
        return title;
    }
}
