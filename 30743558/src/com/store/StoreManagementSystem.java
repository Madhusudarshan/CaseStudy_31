package com.store;

import com.store.model.Customer;
import com.store.model.Order;
import com.store.model.OrderItem;
import com.store.model.Product;
import com.store.service.CustomerService;
import com.store.service.OrderService;
import com.store.service.ProductService;
import com.store.dao.CustomerDAO;
import com.store.dao.OrderDAO;
import com.store.dao.ProductDAO;
import com.store.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class StoreManagementSystem {
//This is the main method program will start running from here.
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Create DAO instances
            CustomerDAO customerDAO = new CustomerDAO(connection);
            OrderDAO orderDAO = new OrderDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);

            // Create Service instances with DAO objects
            CustomerService customerService = new CustomerService(customerDAO);
            OrderService orderService = new OrderService(orderDAO);
            ProductService productService = new ProductService(productDAO);

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("-----------Application Starts---------------");
                System.out.println("1. Product Management");
                System.out.println("2. Customer Management");
                System.out.println("3. Order Management");
                System.out.println("4. Exit");
                System.out.println("---------------------------------------");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        handleProductManagement(scanner, productService);
                        break;
                    case 2:
                        handleCustomerManagement(scanner, customerService);
                        break;
                    case 3:
                        handleOrderManagement(scanner, orderService, productService);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void handleProductManagement(Scanner scanner, ProductService productService) throws SQLException {
        System.out.println("1. Add Product");
        System.out.println("2. View Product");
        System.out.println("3. Update Product");
        System.out.println("4. Delete Product");
        System.out.println("5. View All Products");
        System.out.print("Choose an option: ");

        // Validate integer input
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear the invalid input
        }
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Product product = new Product();
                System.out.print("Enter product name: ");
                product.setName(scanner.next());
                System.out.print("Enter product description: ");
                product.setDescription(scanner.next());

                System.out.print("Enter product price: ");
                while (!scanner.hasNextDouble()) {
                    //System.out.println("Invalid input. Please enter a valid price.");
                    scanner.next(); // Clear the invalid input
                }
                product.setPrice(scanner.nextDouble());

                System.out.print("Enter product stock quantity: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid quantity.");
                    scanner.next(); // Clear the invalid input
                }
                product.setStockQuantity(scanner.nextInt());

                productService.addProduct(product);
                System.out.println("-----Product Added Successfully----");
                break;

            case 2:
                System.out.print("Enter product ID: ");
                int productId = scanner.nextInt();
                Product foundProduct = productService.getProductById(productId);
                System.out.println("Product ID: " + foundProduct.getProductId());
                System.out.println("Name: " + foundProduct.getName());
                System.out.println("Description: " + foundProduct.getDescription());
                System.out.println("Price: " + foundProduct.getPrice());
                System.out.println("Stock Quantity: " + foundProduct.getStockQuantity());
                break;
            case 3:
                System.out.print("Enter product ID: ");
                int updateProductId = scanner.nextInt();
                Product updateProduct = productService.getProductById(updateProductId);
                System.out.print("Enter new name: ");
                updateProduct.setName(scanner.next());
                System.out.print("Enter new description: ");
                updateProduct.setDescription(scanner.next());
                System.out.print("Enter new price: ");
                updateProduct.setPrice(scanner.nextDouble());
                System.out.print("Enter new stock quantity: ");
                updateProduct.setStockQuantity(scanner.nextInt());
                productService.updateProduct(updateProduct);
                break;
            case 4:
                System.out.print("Enter product ID: ");
                int deleteProductId = scanner.nextInt();
                productService.deleteProduct(deleteProductId);
                break;
            case 5:
                List<Product> products = productService.getAllProducts();
                for (Product p : products) {
                    System.out.println("Product ID: " + p.getProductId() + ", Name: " + p.getName());
                }
                break;


            default:
                System.out.println("Invalid option. Please try again.");
        }
    }


    private static void handleCustomerManagement(Scanner scanner, CustomerService customerService) throws SQLException {
        System.out.println("1. Add Customer");
        System.out.println("2. View Customer");
        System.out.println("3. Update Customer");
        System.out.println("4. Delete Customer");
        System.out.println("5. View All Customers");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Customer customer = new Customer();
                System.out.print("Enter customer name: ");
                customer.setName(scanner.next());
                System.out.print("Enter customer email: ");
                customer.setEmail(scanner.next());
                System.out.print("Enter customer phone number: ");
                customer.setPhoneNumber(scanner.next());
                System.out.print("Enter customer address: ");
                customer.setAddress(scanner.next());
                customerService.addCustomer(customer);
                break;
            case 2:
                System.out.print("Enter customer ID: ");
                int customerId = scanner.nextInt();
                Customer foundCustomer = customerService.getCustomerById(customerId);
                if (foundCustomer != null) {
                    System.out.println("Customer ID: " + foundCustomer.getCustomerId());
                    System.out.println("Name: " + foundCustomer.getName());
                    System.out.println("Email: " + foundCustomer.getEmail());
                    System.out.println("Phone Number: " + foundCustomer.getPhoneNumber());
                    System.out.println("Address: " + foundCustomer.getAddress());
                } else {
                    System.out.println("Customer not found.");
                }
                break;
            case 3:
                System.out.print("Enter customer ID: ");
                int updateCustomerId = scanner.nextInt();
                Customer updateCustomer = customerService.getCustomerById(updateCustomerId);
                if (updateCustomer != null) {
                    System.out.print("Enter new name: ");
                    updateCustomer.setName(scanner.next());
                    System.out.print("Enter new email: ");
                    updateCustomer.setEmail(scanner.next());
                    System.out.print("Enter new phone number: ");
                    updateCustomer.setPhoneNumber(scanner.next());
                    System.out.print("Enter new address: ");
                    updateCustomer.setAddress(scanner.next());
                    customerService.updateCustomer(updateCustomer);
                } else {
                    System.out.println("Customer not found.");
                }
                break;
            case 4:
                System.out.print("Enter customer ID: ");
                int deleteCustomerId = scanner.nextInt();
                customerService.deleteCustomer(deleteCustomerId);
                break;
            case 5:
                List<Customer> customers = customerService.getAllCustomers();
                for (Customer c : customers) {
                    System.out.println("Customer ID: " + c.getCustomerId() + ", Name: " + c.getName());
                }
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void handleOrderManagement(Scanner scanner, OrderService orderService, ProductService productService) throws SQLException {
        System.out.println("1. Place Order");
        System.out.println("2. View Order Details");
        System.out.println("3. Update Order");
        System.out.println("4. Cancel Order");
        System.out.println("5. View All Orders");
        System.out.print("Choose an option: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Clear invalid input
        }
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                Order order = new Order();
                System.out.print("Enter customer ID: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid customer ID.");
                    scanner.next(); // Clear invalid input
                }
                order.setCustomerId(scanner.nextInt());
                order.setOrderDate(new Date());

                List<OrderItem> orderItems = new ArrayList<>();
                System.out.print("Enter number of items: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.next(); // Clear invalid input
                }
                int numItems = scanner.nextInt();

                for (int i = 0; i < numItems; i++) {
                    OrderItem item = new OrderItem();

                    System.out.print("Enter product ID: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid product ID.");
                        scanner.next(); // Clear invalid input
                    }
                    item.setProductId(scanner.nextInt());

                    Product product = productService.getProductById(item.getProductId());
                    if (product == null) {
                        System.out.println("Product not found.");
                        return;
                    }

                    System.out.print("Enter quantity: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                        scanner.next(); // Clear invalid input
                    }
                    int quantity = scanner.nextInt();

                    if (quantity > product.getStockQuantity()) {
                        System.out.println("Insufficient stock for product ID: " + item.getProductId());
                        return;
                    }

                    item.setQuantity(quantity);
                    item.setPrice(product.getPrice());

                    // Update product stock
                    product.setStockQuantity(product.getStockQuantity() - quantity);
                    productService.updateProduct(product);

                    orderItems.add(item);
                }

                order.setOrderItems(orderItems);
                order.setTotalAmount(orderItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
                order.setStatus("pending");

                orderService.placeOrder(order);
                System.out.println("Placed Order Successfully");
                break;

            case 2:
                System.out.print("Enter order ID: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid order ID.");
                    scanner.next(); // Clear invalid input
                }
                int orderId = scanner.nextInt();
                Order foundOrder = orderService.getOrderById(orderId);
                if (foundOrder != null) {
                    System.out.println("Order ID: " + foundOrder.getOrderId());
                    System.out.println("Customer ID: " + foundOrder.getCustomerId());
                    System.out.println("Order Date: " + foundOrder.getOrderDate());
                    System.out.println("Total Amount: " + foundOrder.getTotalAmount());
                    System.out.println("Status: " + foundOrder.getStatus());
                    System.out.println("Order Items:");
                    for (OrderItem item : foundOrder.getOrderItems()) {
                        System.out.println("Item ID: " + item.getOrderItemId() + ", Product ID: " + item.getProductId() +
                                ", Quantity: " + item.getQuantity() + ", Price: " + item.getPrice());
                    }
                } else {
                    System.out.println("Order not found.");
                }
                break;

            case 3:
                System.out.print("Enter order ID: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid order ID.");
                    scanner.next(); // Clear invalid input
                }
                int updateOrderId = scanner.nextInt();
                Order updateOrder = orderService.getOrderById(updateOrderId);
                if (updateOrder != null) {
                    System.out.print("Enter new status: ");
                    updateOrder.setStatus(scanner.next());
                    orderService.updateOrder(updateOrder);
                    System.out.println("Updated Order Successfully");
                } else {
                    System.out.println("Order not found.");
                }
                break;

            case 4:
                System.out.print("Enter order ID: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a valid order ID.");
                    scanner.next(); // Clear invalid input
                }
                int cancelOrderId = scanner.nextInt();
                orderService.deleteOrder(cancelOrderId);
                System.out.println("Order Cancelled Successfully");
                break;

            case 5:
                List<Order> orders = orderService.getAllOrders();
                for (Order o : orders) {
                    System.out.println("Order ID: " + o.getOrderId() + ", Customer ID: " + o.getCustomerId() +
                            ", Total Amount: " + o.getTotalAmount() + ", Status: " + o.getStatus());
                }
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }
}
