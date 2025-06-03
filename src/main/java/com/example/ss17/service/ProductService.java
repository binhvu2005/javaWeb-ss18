package com.example.ss17.service;

import com.example.ss17.entity.Product;
import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(Integer id);
}
