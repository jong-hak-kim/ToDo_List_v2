package com.todo.service;

import com.todo.domain.Session;
import com.todo.domain.User;
import com.todo.exception.AlreadyExistsEmailException;
import com.todo.exception.InvalidSigninInformation;
import com.todo.repository.UserRepository;
import com.todo.request.Login;
import com.todo.request.SignUp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signIn(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        Session session = user.addSession();

        return user.getId();
    }

    public void signup(SignUp signup) {

        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        User user = User.builder()
                .name(signup.getName())
                .password(signup.getPassword())
                .email(signup.getEmail())
                .build();


        userRepository.save(user);
    }
}
