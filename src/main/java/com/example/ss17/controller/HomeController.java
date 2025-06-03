package com.example.ss17.controller;

import com.example.ss17.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping({ "/home"})
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());
        return "home";
    }


}
