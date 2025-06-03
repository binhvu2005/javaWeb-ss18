package com.example.ss17.model;

import com.example.ss17.entity.Product;
import com.example.ss17.entity.ProductCart;

public class CartItemView {
    private ProductCart cart;
    private Product product;

    public CartItemView() {
    }

    public CartItemView(ProductCart cart, Product product) {
        this.cart = cart;
        this.product = product;
    }

    public ProductCart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setCart(ProductCart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
