package com.snokant.projekt.Controller;

import com.snokant.projekt.Configuration.JwtConfiguration.JwtConstants;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtGen;
import com.snokant.projekt.Domain.User;
import com.snokant.projekt.Repository.RoleRepository;
import com.snokant.projekt.Repository.UserRepository;
import com.snokant.projekt.Service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest/user")
public class UserController {
    private UserService userService;
    @Autowired
    private JwtGen generator;
    public UserController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("addUser")
    public List<String> addUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        return userService.addNewUser(user,bindingResult);
    }
    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("testuj")
    public String generate(@RequestBody final User user) {
        return generator.generateToken(user);
    }

    @PostMapping("signin")
    public User index(@RequestBody String email) {

        return null;
    }

}
