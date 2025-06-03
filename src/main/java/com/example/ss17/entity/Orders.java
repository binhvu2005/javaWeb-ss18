package com.example.ss17.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer customerId;

    @Column(length = 1000)
    private String listProduct;

    private String recipientName;
    private String phoneNumber;
    private String address;

    private BigDecimal totalMoney;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();
    private String status;

    public String getStatus() {
        return status;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getListProduct() {
        return listProduct;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setListProduct(String listProduct) {
        this.listProduct = listProduct;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
