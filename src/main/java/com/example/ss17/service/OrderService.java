package com.example.ss17.service;

import com.example.ss17.entity.Orders;

import java.util.List;

public interface OrderService {
    void save(Orders order);
    List<Orders> getOrdersByCustomerId(Integer customerId, int page, int size);
    long countOrdersByCustomerId(Integer customerId);
    Orders findById(Integer id);
    void update(Orders order);
}

