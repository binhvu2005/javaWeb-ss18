package com.example.ss17.repository;


import com.example.ss17.entity.ProductCart;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCartRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public ProductCart findByCustomerAndProduct(Integer customerId, Integer productId) {
        String hql = "FROM ProductCart WHERE customerId = :customerId AND productId = :productId";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, ProductCart.class)
                .setParameter("customerId", customerId)
                .setParameter("productId", productId)
                .uniqueResult();
    }

    public void save(ProductCart cart) {
        sessionFactory.getCurrentSession().save(cart);
    }

    public void update(ProductCart cart) {
        sessionFactory.getCurrentSession().update(cart);
    }

    public List<ProductCart> findByCustomerId(Integer customerId) {
        String hql = "FROM ProductCart WHERE customerId = :customerId";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, ProductCart.class)
                .setParameter("customerId", customerId)
                .getResultList();
    }

    public ProductCart findById(Integer cartId) {
        return sessionFactory.getCurrentSession().get(ProductCart.class, cartId);
    }

    public void delete(ProductCart cart) {
        sessionFactory.getCurrentSession().delete(cart);
    }

    public void clearCart(Integer customerId) {
        String hql = "DELETE FROM ProductCart WHERE customerId = :customerId";
        sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("customerId", customerId)
                .executeUpdate();
    }
}
