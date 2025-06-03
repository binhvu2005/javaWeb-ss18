package com.example.ss17.service;

import com.example.ss17.entity.ProductCart;
import com.example.ss17.repository.ProductCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductCartServiceImpl implements ProductCartService {
    @Autowired
    private ProductCartRepository productCartRepository;

    @Override
    public void addToCart(Integer customerId, Integer productId, Integer quantity) {
        ProductCart cart = productCartRepository.findByCustomerAndProduct(customerId, productId);
        if (cart != null) {
            cart.setQuantity(cart.getQuantity() + quantity);
            productCartRepository.update(cart);
        } else {
            ProductCart newCart = new ProductCart();
            newCart.setCustomerId(customerId);
            newCart.setProductId(productId);
            newCart.setQuantity(quantity);
            productCartRepository.save(newCart);
        }
    }

    @Override
    public List<ProductCart> findByCustomerId(Integer customerId) {
        return productCartRepository.findByCustomerId(customerId);
    }

    @Override
    public void deleteCartItem(Integer cartId, Integer customerId) {
        ProductCart cart = productCartRepository.findById(cartId);
        if (cart != null && cart.getCustomerId().equals(customerId)) {
            productCartRepository.delete(cart);
        }
    }

    @Override
    public void clearCart(Integer customerId) {
        productCartRepository.clearCart(customerId);
    }
}
