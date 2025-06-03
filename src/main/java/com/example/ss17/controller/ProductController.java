package com.example.ss17.controller;


import com.example.ss17.entity.Customer;
import com.example.ss17.entity.Product;
import com.example.ss17.service.CustomerService;
import com.example.ss17.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        if (product == null) {
            return "redirect:/home";
        }
        model.addAttribute("product", product);
        return "product-details";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("customer") Customer customer, HttpSession session, Model model) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        user.setPhoneNumber(customer.getPhoneNumber());
        customerService.update(user);
        session.setAttribute("currentUser", user);
        model.addAttribute("success", "Cập nhật số điện thoại thành công!");
        return "redirect:/profile";
    }

}
