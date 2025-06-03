package com.example.ss17.service;


import com.example.ss17.entity.Customer;
import com.example.ss17.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean register(Customer customer, StringBuilder errorMsg) {
        if (customerRepository.findByUsername(customer.getUsername()) != null) {
            errorMsg.append("Tên đăng nhập đã tồn tại!");
            return false;
        }
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            errorMsg.append("Email đã tồn tại!");
            return false;
        }
        customer.setRole("USER");
        customer.setStatus(1);
        customerRepository.save(customer);
        return true;
    }

    @Override
    public Customer login(String username, String password) {
        Customer customer = customerRepository.findByUsername(username);
        if (customer != null && customer.getPassword().equals(password) && customer.getStatus() == 1) {
            return customer;
        }
        return null;
    }

    @Override
    public void update(Customer customer) {
        customerRepository.update(customer);
    }

    @Override
    public Customer findById(Integer id) {
        return customerRepository.findById(id);
    }
}

