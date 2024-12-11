package com.todo.config;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = ToDoMockSecurityContext.class)
public @interface ToDoMockUser {

    String name() default "종학";

    String email() default "whdgkr9070@naver.com";

    String password() default "";

//    String role() default "ROLE_ADMIN";

}
