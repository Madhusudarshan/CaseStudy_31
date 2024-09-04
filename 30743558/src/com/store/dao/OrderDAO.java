package com.store.dao;

import com.store.model.Order;
import com.store.model.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO(Connection connection) {

        this.connection = connection;
    }
//This method is used to place order
    public void placeOrder(Order order) throws SQLException {
        String sqlOrder = "INSERT INTO `Order` (customer_id, order_date, total_amount, status) VALUES (?, ?, ?, ?)";
        String sqlOrderItem = "INSERT INTO OrderItem (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement orderStatement = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemStatement = connection.prepareStatement(sqlOrderItem)) {
            connection.setAutoCommit(false);

            // Insert the order
            orderStatement.setInt(1, order.getCustomerId());
            orderStatement.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            orderStatement.setDouble(3, order.getTotalAmount());
            orderStatement.setString(4, order.getStatus());
            orderStatement.executeUpdate();

            // Get the generated order ID
            ResultSet generatedKeys = orderStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                // Insert each order item
                for (OrderItem item : order.getOrderItems()) {
                    orderItemStatement.setInt(1, orderId);
                    orderItemStatement.setInt(2, item.getProductId());
                    orderItemStatement.setInt(3, item.getQuantity());
                    orderItemStatement.setDouble(4, item.getPrice());
                    orderItemStatement.addBatch();
                }
                orderItemStatement.executeBatch();
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public Order getOrderById(int orderId) throws SQLException {
        String sqlOrder = "SELECT * FROM `Order` WHERE order_id = ?";
        String sqlOrderItems = "SELECT * FROM OrderItem WHERE order_id = ?";
        Order order = null;

        try (PreparedStatement orderStatement = connection.prepareStatement(sqlOrder);
             PreparedStatement orderItemsStatement = connection.prepareStatement(sqlOrderItems)) {

            orderStatement.setInt(1, orderId);
            ResultSet orderResultSet = orderStatement.executeQuery();
            if (orderResultSet.next()) {
                order = new Order();
                order.setOrderId(orderResultSet.getInt("order_id"));
                order.setCustomerId(orderResultSet.getInt("customer_id"));
                order.setOrderDate(orderResultSet.getDate("order_date"));
                order.setTotalAmount(orderResultSet.getDouble("total_amount"));
                order.setStatus(orderResultSet.getString("status"));
            }

            if (order != null) {
                orderItemsStatement.setInt(1, orderId);
                ResultSet orderItemsResultSet = orderItemsStatement.executeQuery();
                List<OrderItem> orderItems = new ArrayList<>();
                while (orderItemsResultSet.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderItemId(orderItemsResultSet.getInt("order_item_id"));
                    item.setOrderId(orderItemsResultSet.getInt("order_id"));
                    item.setProductId(orderItemsResultSet.getInt("product_id"));
                    item.setQuantity(orderItemsResultSet.getInt("quantity"));
                    item.setPrice(orderItemsResultSet.getDouble("price"));
                    orderItems.add(item);
                }
                order.setOrderItems(orderItems);
            }
        }
        return order;
    }

    public void updateOrder(Order order) throws SQLException {
        String sqlOrder = "UPDATE `Order` SET status = ? WHERE order_id = ?";
        try (PreparedStatement orderStatement = connection.prepareStatement(sqlOrder)) {
            orderStatement.setString(1, order.getStatus());
            orderStatement.setInt(2, order.getOrderId());
            orderStatement.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        String sqlOrderItem = "DELETE FROM OrderItem WHERE order_id = ?";
        String sqlOrder = "DELETE FROM `Order` WHERE order_id = ?";
        try (PreparedStatement orderItemStatement = connection.prepareStatement(sqlOrderItem);
             PreparedStatement orderStatement = connection.prepareStatement(sqlOrder)) {
            connection.setAutoCommit(false);

            // Delete order items first
            orderItemStatement.setInt(1, orderId);
            orderItemStatement.executeUpdate();

            // Delete the order
            orderStatement.setInt(1, orderId);
            orderStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sqlOrder = "SELECT * FROM `Order`";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlOrder);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setCustomerId(resultSet.getInt("customer_id"));
                order.setOrderDate(resultSet.getDate("order_date"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                order.setStatus(resultSet.getString("status"));
                orders.add(order);
            }
        }
        return orders;
    }
}
