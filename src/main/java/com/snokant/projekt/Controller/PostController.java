package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Service.ProductService;
import com.snokant.projekt.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest")
public class PostController {
    private ProductService productService;
    private UserService userService;

    public PostController(UserService userService,ProductService productService) {
        this.userService = userService;
        this.productService = productService;

    }

    @PostMapping("user/addUser")
    public List<String> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        return userService.addNewUser(user,bindingResult);
    }
    @PostMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Wylogowano";
    }
}
