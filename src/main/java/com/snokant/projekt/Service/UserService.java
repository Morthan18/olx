package com.snokant.projekt.Service;

import com.snokant.projekt.Domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Transactional
public interface UserService {
    List<String> addNewUser(User user, BindingResult result);
}
