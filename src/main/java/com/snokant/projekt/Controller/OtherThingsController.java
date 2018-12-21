package com.snokant.projekt.Controller;

import com.snokant.projekt.Service.ProductService;
import org.springframework.web.bind.annotation.*;

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
    public List<String> searchByPhrase(@PathVariable String phrase) {
        return productService.findProductNameByNames(phrase);
    }

}
