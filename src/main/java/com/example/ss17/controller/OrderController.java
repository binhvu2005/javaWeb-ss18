package com.example.ss17.controller;


import com.example.ss17.entity.Customer;
import com.example.ss17.entity.Orders;
import com.example.ss17.entity.ProductCart;
import com.example.ss17.service.OrderService;
import com.example.ss17.service.ProductCartService;
import com.example.ss17.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductCartService productCartService;
    @Autowired
    private ProductService productService;

    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        List<ProductCart> cartItems = productCartService.findByCustomerId(user.getId());
        double total = cartItems.stream()
                .mapToDouble(cart -> productService.findById(cart.getProductId()).getPrice().doubleValue() * cart.getQuantity())
                .sum();
        model.addAttribute("order", new Orders());
        model.addAttribute("total", total);
        return "checkout";
    }

    @PostMapping("/submit")
    public String submitOrder(@ModelAttribute("order") Orders order, HttpSession session, Model model) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        List<ProductCart> cartItems = productCartService.findByCustomerId(user.getId());
        if (cartItems.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng của bạn đang trống!");
            return "order-success";
        }
        String listProduct = cartItems.stream()
                .map(c -> c.getProductId() + ":" + c.getQuantity())
                .collect(Collectors.joining(","));
        double total = cartItems.stream()
                .mapToDouble(cart -> productService.findById(cart.getProductId()).getPrice().doubleValue() * cart.getQuantity())
                .sum();

        order.setCustomerId(user.getId());
        order.setListProduct(listProduct);
        order.setTotalMoney(BigDecimal.valueOf(total));
        order.setStatus("PENDING");
        orderService.save(order);

        productCartService.clearCart(user.getId());

        model.addAttribute("message", "Đặt hàng thành công! Cảm ơn bạn đã mua sắm.");
        return "order-success";
    }
}
