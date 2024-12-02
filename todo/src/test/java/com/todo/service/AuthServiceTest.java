package com.todo.service;

import com.todo.domain.User;
import com.todo.exception.AlreadyExistsEmailException;
import com.todo.repository.UserRepository;
import com.todo.request.SignUp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() throws Exception {
        //given
        SignUp signup = SignUp.builder()
                .email("whdgkr9070@naver.com")
                .password("1234")
                .name("jonghak")
                .build();

        //when
        authService.signup(signup);
        //then
        assertEquals(1, userRepository.count());

        User user = userRepository.findAll().iterator().next();
        assertEquals("whdgkr9070@naver.com", user.getEmail());
        assertEquals("1234", user.getPassword());
        assertEquals("jonghak", user.getName());
    }

    @Test
    @DisplayName("회원가입 시 중복된 이메일")
    void test2() throws Exception {

        //given
        User user = User.builder()
                .email("whdgkr9070@naver.com")
                .password("1234")
                .name("joghik")
                .build();

        userRepository.save(user);

        SignUp signup = SignUp.builder()
                .email("whdgkr9070@naver.com")
                .password("1234")
                .name("jonghak")
                .build();

        //expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));

    }
}