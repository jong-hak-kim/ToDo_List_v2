package com.todo.controller;

import com.todo.config.UserPrincipal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String main() {
        return "메인 페이지입니다.";
    }

    //Sprign EL 지원
    //SecurityConfig에서 @EnableMethodSecurity를 선언하게 되면
    //PrePostEnabled가 true로 돼서 접속할 수 있는 역할 설정 가능
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return "사용자 페이지입니다. 🥹";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String admin() {
        return "관리자 페이지입니다. 😍";
    }
}
