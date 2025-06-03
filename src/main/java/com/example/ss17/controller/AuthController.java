package com.example.ss17.controller;


import com.example.ss17.entity.Customer;
import com.example.ss17.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("customer") @Valid Customer customer,
                           BindingResult result,
                           Model model) {
        StringBuilder errorMsg = new StringBuilder();
        if (!result.hasErrors()) {
            if (!customerService.register(customer, errorMsg)) {
                result.rejectValue("username", "error.customer", errorMsg.toString());
            }
        }
        if (result.hasErrors()) {
            return "register";
        }
        model.addAttribute("success", "Đăng ký thành công! Bạn có thể đăng nhập.");
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("customer") Customer customer,
                        Model model,
                        HttpSession session) {
        if (customer.getUsername() == null || customer.getUsername().isEmpty()) {
            model.addAttribute("error", "Tên đăng nhập không được để trống");
            return "login";
        }
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            model.addAttribute("error", "Mật khẩu không được để trống");
            return "login";
        }
        Customer loginUser = customerService.login(customer.getUsername(), customer.getPassword());
        if (loginUser == null) {
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu hoặc tài khoản bị khóa");
            return "login";
        }
        session.setAttribute("currentUser", loginUser);
        if ("ADMIN".equals(loginUser.getRole())) return "redirect:/admin";
        else return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
