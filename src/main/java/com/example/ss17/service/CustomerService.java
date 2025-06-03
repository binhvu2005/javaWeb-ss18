package com.example.ss17.service;


import com.example.ss17.entity.Customer;

public interface CustomerService {
    boolean register(Customer customer, StringBuilder errorMsg);
    Customer login(String username, String password);
    void update(Customer customer);
    Customer findById(Integer id);
}

