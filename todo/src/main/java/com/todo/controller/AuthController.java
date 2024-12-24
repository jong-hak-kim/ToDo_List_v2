package com.todo.controller;

import com.todo.config.AppConfig;
import com.todo.request.SignUp;
import com.todo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/api/auth/signup")
    public void signup(@RequestBody SignUp signup) {
        authService.signup(signup);
    }
}
