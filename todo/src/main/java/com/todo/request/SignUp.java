package com.todo.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUp {

    private String email;
    private String name;
    private String password;

}