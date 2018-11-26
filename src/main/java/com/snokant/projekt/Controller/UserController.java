package com.snokant.projekt.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Domain.UserRequestLogin;
import com.snokant.projekt.Service.UserService;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/user/")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("addUser")
    public List<String> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return userService.addNewUser(user, bindingResult);
    }

    @PostMapping("signIn")
    public List<String> generate(@Valid @RequestBody UserRequestLogin user) {
        return userService.signIn(user);
    }
}
