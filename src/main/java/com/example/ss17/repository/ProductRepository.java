package com.example.ss17.repository;


import com.example.ss17.entity.Product;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public List<Product> findAll() {
        String hql = "FROM Product";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Product.class)
                .getResultList();
    }

    public Product findById(Integer id) {
        return sessionFactory.getCurrentSession().get(Product.class, id);
    }
}

