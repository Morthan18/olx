package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rest")
public class OtherThingsController {
    ProductService productService;

    public OtherThingsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("search/{phrase}")
    public List<Product> searchByPhrase(@PathVariable String phrase) {
        return productService.searchByPhrase(phrase);
    }
    @PostMapping("/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Wylogowano";
    }
    @PostMapping("wyjdz")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
