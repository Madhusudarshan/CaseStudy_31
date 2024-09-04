package com.store.service;

import com.store.dao.ProductDAO;
import com.store.model.Product;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {

        this.productDAO = productDAO;
    }

    public void addProduct(Product product) throws SQLException {
        productDAO.addProduct(product);
    }

    public Product getProductById(int productId) throws SQLException {
        return productDAO.getProductById(productId);
    }

    public void updateProduct(Product product) throws SQLException {
        productDAO.updateProduct(product);
    }

    public void deleteProduct(int productId) throws SQLException {
        productDAO.deleteProduct(productId);
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.getAllProducts();
    }
}
