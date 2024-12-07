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
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

//비밀번호 암호화
//1. 해시
//2. 해시 방식
//1. SHA1
//2. SHA256
//3. MD5
//4. 왜 이런 걸로 비번 암호화하면 안되는지
//3. BCrypt SCrypt, Argon2
//4. salt 값


@SpringBootTest
@ActiveProfiles("test")
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
        assertNotNull(user.getPassword());
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
                .name("jonghak")
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