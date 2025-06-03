package com.example.ss17.controller;


import com.example.ss17.entity.Customer;
import com.example.ss17.entity.Orders;
import com.example.ss17.service.CustomerService;
import com.example.ss17.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/profile")
    public String profile(HttpSession session,
                          @RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "5") int size,
                          Model model) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("customer", user);

        List<Orders> orders = orderService.getOrdersByCustomerId(user.getId(), page, size);
        long totalOrders = orderService.countOrdersByCustomerId(user.getId());
        int totalPages = (int) Math.ceil((double) totalOrders / size);

        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute("customer") Customer customer, HttpSession session, Model model) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        customer.setId(user.getId());
        customerService.update(customer);
        session.setAttribute("currentUser", customer);
        model.addAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/profile";
    }

    @PostMapping("/order/cancel/{orderId}")
    public String cancelOrder(@PathVariable Integer orderId, HttpSession session) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";
        Orders order = orderService.findById(orderId);
        if (order != null && order.getCustomerId().equals(user.getId())) {
            order.setStatus("CANCELLED");
            orderService.update(order);
        }
        return "redirect:/profile";
    }
}
