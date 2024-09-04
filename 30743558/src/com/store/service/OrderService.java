
package com.store.service;

import com.store.dao.OrderDAO;
import com.store.model.Order;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {

        this.orderDAO = orderDAO;
    }

    public void placeOrder(Order order) throws SQLException {
        orderDAO.placeOrder(order);
    }

    public Order getOrderById(int orderId) throws SQLException {
        return orderDAO.getOrderById(orderId);
    }

    public void updateOrder(Order order) throws SQLException {
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int orderId) throws SQLException {
        orderDAO.deleteOrder(orderId);
    }

    public List<Order> getAllOrders() throws SQLException {
        return orderDAO.getAllOrders();
    }
}
