package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Domain.UserRequestLogin;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;


public interface UserService {
    List<String> addNewUser(User user, BindingResult result);
    User loadUserByUsername(String username) throws UsernameNotFoundException;
    List<String> signIn(UserRequestLogin user);
}
