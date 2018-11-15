package com.snokant.projekt.Controller;

import com.snokant.projekt.Domain.Product;
import com.snokant.projekt.Service.ProductService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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
}
