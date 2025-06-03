package com.example.ss17.service;

import com.example.ss17.entity.ProductCart;

import java.util.List;

public interface ProductCartService {
    void addToCart(Integer customerId, Integer productId, Integer quantity);
    List<ProductCart> findByCustomerId(Integer customerId);
    void deleteCartItem(Integer cartId, Integer customerId);
    void clearCart(Integer customerId);
}
