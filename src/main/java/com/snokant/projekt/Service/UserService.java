package com.snokant.projekt.Service;

import com.snokant.projekt.Model.Product;
import com.snokant.projekt.Model.User;
import com.snokant.projekt.Model.Domain.UserDomain;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;

import java.util.List;


public interface UserService {
    List<String> addNewUser(User user);

    List<String> logIn(User user);

    List<String> modifyProfile(User user, String token);

    User getUserDetails(String token);

    List<String> sendRequestForPhoneNumber(int userIdThatPhoneNumberIWant, String token);

    List<User> getMyRequestList(String token);

    List<String> acceptRequestPhone(String token, int userId);
}
