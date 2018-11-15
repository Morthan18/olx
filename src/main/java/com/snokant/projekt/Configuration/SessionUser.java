package com.snokant.projekt.Configuration;

import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.UserRepository;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class SessionUser {
    private User user;
    UserRepository userRepository;


    public SessionUser(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        if (user == null) {
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            user = userRepository.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return user;
    }

}
