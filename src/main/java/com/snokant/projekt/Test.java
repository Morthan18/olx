package com.snokant.projekt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping("/index")
    public String s(){
        return "index.html";
    }
    @GetMapping("/witam")
    public String sa(){
        return "witam.html";
    }
    @GetMapping("/test")
    public String asa(){
        return "access-denied.html";
    }
}
