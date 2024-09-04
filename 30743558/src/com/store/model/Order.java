package com.store.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//This is Order CLass  and created getters and setters
public class Order {
    private int orderId;
    private int customerId;
    private Date orderDate;
    private double totalAmount;
    private String status;
    private List<OrderItem> orderItems;

    public Order() {

        this.orderItems = new ArrayList<>();
    }

    public int getOrderId() {

        return orderId;
    }

    public void setOrderId(int orderId) {

        this.orderId = orderId;
    }

    public int getCustomerId() {

        return customerId;
    }

    public void setCustomerId(int customerId) {

        this.customerId = customerId;
    }

    public Date getOrderDate() {

        return orderDate;
    }

    public void setOrderDate(Date orderDate) {

        this.orderDate = orderDate;
    }

    public double getTotalAmount() {

        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {

        this.totalAmount = totalAmount;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public List<OrderItem> getOrderItems() {

        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {

        this.orderItems = orderItems;
    }

    public void addOrderItem(int productId, int quantity, double price) {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setPrice(price);
        this.orderItems.add(item);
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (OrderItem item : orderItems) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
