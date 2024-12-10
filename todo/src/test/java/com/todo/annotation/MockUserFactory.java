//package com.todo.annotation;
//
//import com.todo.repository.UserRepository;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//public class MockUserFactory implements WithSecurityContextFactory<CustomWithMockUser> {
//
//    private UserRepository userRepository;
//
//    @Override
//    public SecurityContext createSecurityContext(CustomWithMockUser annotation) {
//        Integer level = annotation.level();
//        String username = annotation.username();
//        userRepository.save(
//                //
//        )
//    }
//}
