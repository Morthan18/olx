package com.snokant.projekt.Controller;

import com.snokant.projekt.Model.User;
import com.snokant.projekt.Service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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
    public List<String> addUser(@Valid @RequestBody User user) {
        return userService.addNewUser(user);
    }

    @PostMapping("signIn")
    public List<String> logIn(@Valid @RequestBody User user) {
        return userService.logIn(user);
    }
    @GetMapping("userDetails")
    public User getUserDetails(
            @RequestHeader("Authorization") String token){
        return userService.getUserDetails(token);
    }
    @PostMapping("modifyProfile")
    public List<String> modifyProfile(
            @Valid @RequestBody User user,
            @RequestHeader("Authorization") String token){
        return userService.modifyProfile(user,token);
    }
    @GetMapping("sendRequestPhone/{userId}")
    public List<String> requestPhoneNumber(
            @PathVariable("userId") int userIdThatPhoneNumberIWant,
            @RequestHeader("Authorization") String token){
        return userService.sendRequestForPhoneNumber(userIdThatPhoneNumberIWant,token);
    }
    @GetMapping("myRequestList")
    public List<User> getMyRequestList(
            @RequestHeader("Authorization") String token){
        return userService.getMyRequestList(token);
    }
    @GetMapping("acceptRequestPhone/{userId}")
    public List<String> acceptRequestPhone(
            @RequestHeader("Authorization") String token,
            @PathVariable("userId") int userId){
        if(userId!=0) {
            return userService.acceptRequestPhone(token, userId);
        }
        return Arrays.asList("BLAD","Wpisales zero");
    }
}