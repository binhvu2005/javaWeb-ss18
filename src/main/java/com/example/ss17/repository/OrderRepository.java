package com.example.ss17.repository;

import com.example.ss17.entity.Orders;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderRepository {
    @Autowired
    private SessionFactory sessionFactory;

    public void save(Orders order) {
        sessionFactory.getCurrentSession().save(order);
    }

    public List<Orders> findByCustomerIdWithPagination(Integer customerId, int page, int size) {
        String hql = "FROM Orders WHERE customerId = :customerId ORDER BY createdAt DESC";
        return sessionFactory.getCurrentSession()
                .createQuery(hql, Orders.class)
                .setParameter("customerId", customerId)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    public long countByCustomerId(Integer customerId) {
        String hql = "SELECT COUNT(*) FROM Orders WHERE customerId = :customerId";
        return (Long) sessionFactory.getCurrentSession()
                .createQuery(hql)
                .setParameter("customerId", customerId)
                .uniqueResult();
    }

    public Orders findById(Integer id) {
        return sessionFactory.getCurrentSession().get(Orders.class, id);
    }

    public void update(Orders order) {
        sessionFactory.getCurrentSession().update(order);
    }
}

