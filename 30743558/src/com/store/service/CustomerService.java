package com.store.service;

import com.store.dao.CustomerDAO;
import com.store.model.Customer;
import java.sql.SQLException;
import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;

    public CustomerService(CustomerDAO customerDAO) {

        this.customerDAO = customerDAO;
    }

    public void addCustomer(Customer customer) throws SQLException {
        customerDAO.addCustomer(customer);
    }

    public Customer getCustomerById(int customerId) throws SQLException {
        return customerDAO.getCustomerById(customerId);
    }

    public void updateCustomer(Customer customer) throws SQLException {
        customerDAO.updateCustomer(customer);
    }

    public void deleteCustomer(int customerId) throws SQLException {
        customerDAO.deleteCustomer(customerId);
    }

    public List<Customer> getAllCustomers() throws SQLException {
        return customerDAO.getAllCustomers();
    }
}
