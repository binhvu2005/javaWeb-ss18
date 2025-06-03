package com.example.ss17.controller;

import com.example.ss17.entity.Customer;
import com.example.ss17.entity.Product;
import com.example.ss17.entity.ProductCart;
import com.example.ss17.model.CartItemView;
import com.example.ss17.service.ProductCartService;
import com.example.ss17.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductCartService productCartService;

    @Autowired
    private ProductService productService;

    @PostMapping("/add/{productId}")
    public String addToCart(@PathVariable("productId") Integer productId,
                            @RequestParam("quantity") Integer quantity,
                            HttpSession session) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        productCartService.addToCart(user.getId(), productId, quantity);
        return "redirect:/cart/view";
    }

    @GetMapping("/view")
    public String viewCart(HttpSession session, Model model) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        List<ProductCart> cartItems = productCartService.findByCustomerId(user.getId());
        List<CartItemView> cartViewList = new ArrayList<>();
        double total = 0;
        for (ProductCart cart : cartItems) {
            Product product = productService.findById(cart.getProductId());
            if (product != null) {
                cartViewList.add(new CartItemView(cart, product));
                total += product.getPrice().doubleValue() * cart.getQuantity();
            }
        }
        model.addAttribute("cartItems", cartViewList);
        model.addAttribute("total", total);
        return "cart";
    }

    @GetMapping("/delete/{cartId}")
    public String deleteCartItem(@PathVariable("cartId") Integer cartId, HttpSession session) {
        Customer user = (Customer) session.getAttribute("currentUser");
        if (user == null) {
            return "redirect:/login";
        }
        productCartService.deleteCartItem(cartId, user.getId());
        return "redirect:/cart/view";
    }

}
