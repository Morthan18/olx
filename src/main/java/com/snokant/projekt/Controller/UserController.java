package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Service.ProductService;
import com.snokant.projekt.Service.UserService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }

    @PostMapping("addUser")
    public List<String> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return userService.addNewUser(user,bindingResult);
    }
}
